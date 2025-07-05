package com.app.playerservicejava.service;
import com.app.playerservicejava.Exceptions.BadRequestException;
import com.app.playerservicejava.Exceptions.NotFoundException;
import com.app.playerservicejava.utils.PlayerUtils;
import com.app.playerservicejava.model.Player;
import com.app.playerservicejava.model.PlayerCreateDTO;
import com.app.playerservicejava.model.PlayerResponseDTO;
import com.app.playerservicejava.repository.PlayerRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;



@Service
public class PlayerServiceV2_0 {

    @Autowired
    private PlayerRepository playerRepository;
    private final Map<String, PlayerResponseDTO> inMemoryCache = new ConcurrentHashMap<>();

    // Get all players based on isAdmin flag
    public List<PlayerResponseDTO> getAllPlayers(boolean isAdmin) {
        List<Player> players = playerRepository.findAll();
        return players.stream()
                .map(player -> PlayerUtils.convertToDto(player, isAdmin))
                .collect(Collectors.toList());
    }

    // Get player by ID
    public PlayerResponseDTO getPlayerById(String id) throws NotFoundException {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Player not found"));
        return PlayerUtils.convertToDto(player, true);
    }

    // Create a new player
    public Player createPlayer(PlayerCreateDTO playerCreateDTO) {
        Player player = new Player();
        player.setPlayerId(null);
        player.setFirstName(playerCreateDTO.getFirstName());
        player.setLastName(playerCreateDTO.getLastName());
        player.setBirthYear(playerCreateDTO.getBirthYear());
        player.setBirthMonth(playerCreateDTO.getBirthMonth());
        player.setBirthDay(playerCreateDTO.getBirthDay());
        player.setBirthCountry(playerCreateDTO.getBirthCountry());
        player.setBirthState(playerCreateDTO.getBirthState());
        player.setBirthCity(playerCreateDTO.getBirthCity());
        player.setDeathYear(playerCreateDTO.getDeathYear());
        player.setDeathMonth(playerCreateDTO.getDeathMonth());
        player.setDeathDay(playerCreateDTO.getDeathDay());
        player.setDeathCountry(playerCreateDTO.getDeathCountry());
        player.setDeathState(playerCreateDTO.getDeathState());
        player.setDeathCity(playerCreateDTO.getDeathCity());
        player.setGivenName(playerCreateDTO.getGivenName());
        player.setWeight(playerCreateDTO.getWeight());
        player.setHeight(playerCreateDTO.getHeight());
        player.setBats(playerCreateDTO.getBats());
        player.setThrowStats(playerCreateDTO.getThrowStats());
        player.setDebut(playerCreateDTO.getDebut());
        player.setFinalGame(playerCreateDTO.getFinalGame());
        player.setRetroId(playerCreateDTO.getRetroId());
        player.setBbrefId(playerCreateDTO.getBbrefId());

        // Save the player
        return playerRepository.save(player);
    }

    // Update an existing player
    public PlayerResponseDTO updatePlayer(String id, PlayerCreateDTO playerCreateDTO) throws NotFoundException {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Player not found"));

        player.setFirstName(playerCreateDTO.getFirstName());
        player.setLastName(playerCreateDTO.getLastName());
        player.setBirthYear(playerCreateDTO.getBirthYear());
        player.setHeight(playerCreateDTO.getHeight());
        player.setWeight(playerCreateDTO.getWeight());
        player.setBats(playerCreateDTO.getBats());
        player.setThrowStats(playerCreateDTO.getThrowStats());

        player = playerRepository.save(player);
        return PlayerUtils.convertToDto(player, true);
    }

    // Get player's age
    public Integer getPlayerAge(String id) throws NotFoundException {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Player not found"));

        return PlayerUtils.calculateAge(player);
    }
    // Get players sorted by weight or height
    public List<PlayerResponseDTO> getSortedPlayers() throws BadRequestException {
        List<Player> players = playerRepository.findAll();


        // Use a stream to filter and sort players by weight and height
        players = players.stream()
                .filter(player-> ObjectUtils.isNotEmpty(player.getWeight()) &&  ObjectUtils.isNotEmpty(player.getHeight()))
                .sorted(Comparator.comparing((Player p)->Integer.parseInt(p.getHeight())).thenComparing(p->Integer.parseInt(p.getHeight()))).collect(Collectors.toList());
        // Convert players to PlayerResponseDTO
        return players.stream()
                .map(player -> PlayerUtils.convertToDto(player, true))
                .collect(Collectors.toList());
    }
    // Get player by ID with Redis caching
    @Cacheable(value = "players", key = "#id")
    public PlayerResponseDTO getPlayerByIdWithCache(String id) {
        return playerRepository.findById(id)
                .map(player -> PlayerUtils.convertToDto(player, true))
                .orElseThrow(() -> new ResourceNotFoundException("Player not found with ID: " + id));
    }

    // Get player by ID using in-memory caching
    public PlayerResponseDTO getPlayerByIdInMemoryCache(String id) {
        return inMemoryCache.computeIfAbsent(id, key -> {
            Player player = playerRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Player not found with ID: " + id));
            return PlayerUtils.convertToDto(player, true);
        });
    }


    public Page<PlayerResponseDTO> getPlayersPaginate(Integer page, Integer size) {
        Pageable pagable= PageRequest.of(page,size);
        Page<Player> players=playerRepository.findAll(pagable);

        return players.map(player->PlayerUtils.convertToDto(player,true));
    }
}