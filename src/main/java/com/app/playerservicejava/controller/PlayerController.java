package com.app.playerservicejava.controller;

import com.app.playerservicejava.model.Player;
import com.app.playerservicejava.model.PlayerDto;
import com.app.playerservicejava.model.Players;
import com.app.playerservicejava.service.PlayerService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(value = "v1/players", produces = { MediaType.APPLICATION_JSON_VALUE })
public class PlayerController {
    @Resource
    private PlayerService playerService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Players> getPlayers() {
        Players players = playerService.getPlayers();
        return ok(players);
    }

    @PostMapping
    public ResponseEntity<String> addPLayer(@Valid PlayerDto player) //@Valid tag to check for the non null fields in the DTO
    {
        if(1 == 2) //Can make it true to simulate Global Advice
        {
            throw  new RuntimeException(); //Using this to test the exception handling by the global exception handler, i.e // PlayerControllerAdvice.java
        }
        return  ok(player.getFirstName());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePlayer(@PathVariable("id") String id, @Valid PlayerDto player) {
        if (id == null || id.isEmpty()) {
            return new ResponseEntity<>("Player ID cannot be null or empty", HttpStatus.BAD_REQUEST);
        }
        Optional<Player> existingPlayer = playerService.getPlayerById(id);

        if (!existingPlayer.isPresent()) {
            return new ResponseEntity<>("Player not found", HttpStatus.NOT_FOUND);
        }
        boolean updateResult = playerService.updatePlayer(id, existingPlayer.get(), player);
        return new ResponseEntity<>(player.getFirstName(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable("id") String id) {
        Optional<Player> player = playerService.getPlayerById(id);
        if(1 == 2 )
        {
            throw  new RuntimeException(); //Using this to test the exception handling by the global exception handler, i.e // PlayerControllerAdvice.java
        }
        if (player.isPresent()) {
            return new ResponseEntity<>(player.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
