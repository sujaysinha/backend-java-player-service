package com.app.playerservicejava.service;

import com.app.playerservicejava.model.Player;
import com.app.playerservicejava.service.chat.ChatClientService;
import com.app.playerservicejava.utils.PlayerUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.ollama4j.models.OllamaResult;
import io.github.ollama4j.types.OllamaModelType;
import io.github.ollama4j.utils.OptionsBuilder;
import io.github.ollama4j.utils.PromptBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OllammaService {
private final Logger logger= LoggerFactory.getLogger(OllammaService.class);
    @Autowired
    PlayerService playerService;

    @Autowired
    private ChatClientService chatClientService;

    public String generateNickname(String playerName, String country) throws Exception {
        String model = OllamaModelType.TINYLLAMA;

        // Customize the prompt for nickname generation
        PromptBuilder promptBuilder = new PromptBuilder()
                .addLine("You are an expert in sports nicknames.")
                .addLine("Based on the player's name and country, generate a unique and culturally relevant nickname.")
                .addLine("Player name: " + playerName)
                .addLine("Country: " + country);

        boolean raw = false;
        logger.info("Generated prompt: {}", promptBuilder.build());
        // Generate nickname using LLM
        OllamaResult response = chatClientService.getOllamaAPI().generate(
                model,
                promptBuilder.build(),
                raw,
                new OptionsBuilder().build()
        );

        return response.getResponse();
    }
    public String generateSelectedPlayersForTournaments() throws Exception {
        // Fetch and limit players
        List<Player> players = playerService.getPlayers().getPlayers().stream()
                .limit(50)
                .collect(Collectors.toList());

        // Validate players list
        if (players == null || players.isEmpty()) {
            throw new IllegalStateException("No players available for recommendation.");
        }

        // Convert players to messages list
        String playerDetails = players.stream()
                .map(player -> {
                    try {
                        return PlayerUtils.createRecommendationRequest(player);
                    } catch (JsonProcessingException e) {
                        throw new IllegalArgumentException("Error processing player: " + player, e);
                    }
                })
                .collect(Collectors.joining(","));

        // Validate messages list
        if (playerDetails.isEmpty()) {
            throw new IllegalStateException("No valid player data available for prompt generation.");
        }

        // Construct SYSTEM and USER prompts
        String systemPrompt = "You are an expert in selecting the most qualified players for upcoming tournaments. " +
                "Consider player demographics and match statistics to provide the best recommendations."+"Players: " + playerDetails;

        //Another Sample Prompt for this
        /*
         *        PromptBuilder promptBuilder = new PromptBuilder()
                .addLine("You are an expert in selecting the most qualified players for upcoming tournaments. ").addSeparator()
                .addLine("Consider player demographics and match statistics to provide the best recommendations.").addSeparator()
                .addLine("Please give me just the list of players from the given below data "+playerDetails).addSeparator()
                .addLine("Analyse the data and return a proper structured reasoning for the exact players who are being selected");
         * */
        // Log the prompts for debugging
        logger.info("System Prompt: {}", systemPrompt);

        // Call AI service with SYSTEM and USER prompts
        OllamaResult response = chatClientService.getOllamaAPI().generate(
                OllamaModelType.TINYLLAMA,
                new PromptBuilder().addLine(systemPrompt).build(),false,
                new OptionsBuilder().build()
        );

        // Validate AI Response
        if (response == null || response.getResponse() == null || response.getResponse().isEmpty()) {
            throw new IllegalStateException("AI service returned an invalid or empty response.");
        }

        return response.getResponse();
    }


}