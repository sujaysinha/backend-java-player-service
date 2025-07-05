package com.app.playerservicejava.utils;

import com.app.playerservicejava.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;


public class PlayerUtils {
    private static Logger logger= LoggerFactory.getLogger(PlayerUtils.class);
    // Convert Player entity to PlayerResponseDTO
    public static PlayerResponseDTO convertToDto(Player player, boolean isAdmin) {
        PlayerResponseDTO dto = new PlayerResponseDTO();
        dto.setFirstName(player.getFirstName());

        if (isAdmin) {
            dto.setLastName(player.getLastName());
        }

        return dto;
    }

    // Calculate player's age based on birth year and death year (if available)
    public static Integer calculateAge(Player player) {
        int currentYear = LocalDate.now().getYear();
        int birthYear = Integer.parseInt(player.getBirthYear());
        int deathYear = player.getDeathYear() != null ? Integer.parseInt(player.getDeathYear()) : currentYear;

        return deathYear - birthYear;
    }

    public static void validateRecommendationRequest(Recommendation_Request recommendationRequest) {
        if (ObjectUtils.isEmpty(recommendationRequest) && ObjectUtils.isEmpty(recommendationRequest.getCriteria())) {
            throw new RuntimeException("please provided valid recommendation criteria.The values provided cannot be null or empty");
        }
    }

    public static String createRecommendationRequest(Player player) throws JsonProcessingException {
       return  String.format("Name: %s %s, Height: %s, Weight: %s, Matches Played: %s",
               player.getFirstName(), player.getLastName(),
               player.getHeight(), player.getWeight(),
               player.getBirthCountry());


    }
    public static Boolean validateValidPlayer(Player player,PlayerCreateDTO playerCreateDTO){
        try{
        Integer expectedWeight=Integer.parseInt(player.getWeight());
        Integer currentWeight=Integer.parseInt(playerCreateDTO.getWeight());

        Integer expectedHeight=Integer.parseInt(player.getHeight());
        Integer currentHeight=Integer.parseInt(playerCreateDTO.getHeight());
        String expBirthCountry=player.getBirthCountry();
        String currBirthCountry=playerCreateDTO.getBirthCountry();
        Integer currBirthYear=Integer.parseInt(playerCreateDTO.getBirthYear());
        Integer expBirthYear=Integer.parseInt(player.getBirthYear());
        if(currentWeight>=expectedWeight || currentHeight>=expectedHeight || expBirthCountry==currBirthCountry ||
                currBirthYear>=expBirthYear){
            return Boolean.TRUE;
        }
    }catch (NumberFormatException e){
            logger.info(e.getMessage());
        }
        return Boolean.FALSE;
    }


}