package com.app.playerservicejava.controller;

import com.app.playerservicejava.Exceptions.BadRequestException;
import com.app.playerservicejava.Exceptions.NotFoundException;
import com.app.playerservicejava.model.Player;
import com.app.playerservicejava.model.PlayerCreateDTO;
import com.app.playerservicejava.model.PlayerResponseDTO;
import com.app.playerservicejava.service.PlayerServiceV2_0;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v2/players")
public class PlayerController_Update {
    @Autowired
    private PlayerServiceV2_0 playerService;

    // Get all players with isAdmin flag
    @GetMapping
    public List<PlayerResponseDTO> getAllPlayers(@RequestParam boolean isAdmin) {
        return playerService.getAllPlayers(isAdmin);
    }

    // Get player by ID
    @GetMapping("/{id}")
    public PlayerResponseDTO getPlayerById(@PathVariable String id) throws NotFoundException {
        try {
            return playerService.getPlayerById(id);
        }
        catch (NotFoundException e){
            throw new NotFoundException(e.getMessage());
        }
        catch (Exception e){
            throw new NotFoundException(e.getMessage());
        }
    }

    // Create a new player
    @PostMapping("/player")
    public Player createPlayer(@Valid @RequestBody PlayerCreateDTO playerCreateDTO) {
        return playerService.createPlayer(playerCreateDTO);
    }

    // Update an existing player
    @PutMapping("/{id}")
    public PlayerResponseDTO updatePlayer(@PathVariable String id, @Valid @RequestBody PlayerCreateDTO playerCreateDTO) throws NotFoundException {
        return playerService.updatePlayer(id, playerCreateDTO);
    }

    // Get player's age
    @GetMapping("/{id}/age")
    public Integer getPlayerAge(@PathVariable String id) throws NotFoundException {
        return playerService.getPlayerAge(id);
    }

    // Get players sorted by weight or height
    @GetMapping("/sorted")
    public List<PlayerResponseDTO> getSortedPlayers() throws BadRequestException {
        return playerService.getSortedPlayers();
    }

    @GetMapping("reddis/{id}")
    public ResponseEntity<PlayerResponseDTO> getPlayerByIdReddis(@PathVariable String id) {
        PlayerResponseDTO player = playerService.getPlayerByIdWithCache(id);
        return ResponseEntity.ok(player);
    }

    // API to get player by ID using in-memory caching
    @GetMapping("/in-memory/{id}")
    public ResponseEntity<PlayerResponseDTO> getPlayerByIdInMemory(@PathVariable String id) {
        PlayerResponseDTO player = playerService.getPlayerByIdInMemoryCache(id);
        return ResponseEntity.ok(player);
    }
    @GetMapping("pagable")
    public Page<PlayerResponseDTO> PaginatePlayers(@RequestParam (defaultValue = "0") Integer page,
                                                   @RequestParam (defaultValue = "10") Integer size){
        return playerService.getPlayersPaginate(page,size);
    }
}