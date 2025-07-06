package com.app.playerservicejava.controller.chat;

import com.app.playerservicejava.model.Recommendation_Request;
import com.app.playerservicejava.service.OllammaService;
import com.app.playerservicejava.service.chat.ChatClientService;
import com.app.playerservicejava.service.OllammaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.models.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "v1/chat", produces = { MediaType.APPLICATION_JSON_VALUE })
public class ChatController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    private ChatClientService chatClientService;
    @Autowired
    private OllammaService ollammaService;
    @PostMapping
    public @ResponseBody String chat() throws OllamaBaseException, IOException, InterruptedException {
        return chatClientService.chat();
    }

    @GetMapping("/list-models")
    public ResponseEntity<List<Model>> listModels() throws OllamaBaseException, IOException, URISyntaxException, InterruptedException {
        List<Model> models = chatClientService.listModels();
        return ResponseEntity.ok(models);
    }
    //Another code way of generating nickname using System and User prompt
    //https://github.com/Intuit-A4A/backend-java-player-service/compare/main...bhuva-v:backend-java-player-service:main

    //Another way to generate nickname with some JSON format given, have not run this before but can try out
    // https://github.com/Intuit-A4A/backend-java-player-service/compare/main...shreya-k:backend-java-player-service:main
    @PostMapping("/generateNickname")
    public ResponseEntity<Map<String, String>> generateNickname(@RequestParam String country,@RequestParam String playerName) {
        try {
            String nickname = ollammaService.generateNickname(playerName, country);
            Map<String, String> response = new HashMap<>();
            response.put("playerName", playerName);
            response.put("country", country);
            response.put("nickname", nickname);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to generate nickname: " + e.getMessage()));
        }
    }
    @PostMapping("/selectedPlayers")
    public Object selectedPlayers() {
        try {
            String responseEntity= ollammaService.generateSelectedPlayersForTournaments();
            return ResponseEntity.ok(responseEntity);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to generate nickname: " + e.getMessage()));
        }
    }
}