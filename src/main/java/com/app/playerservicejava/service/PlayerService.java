package com.app.playerservicejava.service;

import com.app.playerservicejava.model.Player;
import com.app.playerservicejava.model.PlayerDto;
import com.app.playerservicejava.model.Players;
import com.app.playerservicejava.repository.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerService.class);

    @Autowired
    private PlayerRepository playerRepository;

    @Cacheable(value = "players")
    public Players getPlayers() {
        System.out.println("Querying players from the database...");
        Players players = new Players();
        playerRepository.findAll()
                .forEach(players.getPlayers()::add);
        return players;
    }
    @Cacheable(value = "player", key = "#playerId")
    //If we place the CacheEvict here, it will delete every time id from cache when this method is called
   // @CacheEvict(value="player", key = "#playerId") // This will evict the cache for the player with the given ID when this method is called
    // Also can have @CacheEvict(value="players", allEntries=true) if we want to clear the players cache when a player is updated or deleted
    public Optional<Player> getPlayerById(String playerId) {
        Optional<Player> player = null;

        /* simulated network delay */
        try {
            System.out.println("Querying player with ID: " + playerId + " from the database...");
            player = playerRepository.findById(playerId);
           // Thread.sleep((long)(Math.random() * 2000));
        } catch (Exception e) {
            LOGGER.error("message=Exception in getPlayerById; exception={}", e.toString());
            return Optional.empty();
        }
        return player;
    }
    @CacheEvict(value="player", key = "#playerId")
    public boolean updatePlayer(String playerId, Player player, PlayerDto playerDto) {
        if (playerRepository.existsById(playerId)) {
            player.setBirthYear(playerDto.getBirthYear()); //Just simulating an update on birthYear
            playerRepository.save(player);
            return true;
        } else {
            LOGGER.warn("message=Player with ID {} not found for update", playerId);
            return false;
        }
    }

}
