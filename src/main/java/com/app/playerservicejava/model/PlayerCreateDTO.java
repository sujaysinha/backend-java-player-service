package com.app.playerservicejava.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;




@Getter
@Setter
public class PlayerCreateDTO {

    @NotNull(message = "First name cannot be null")
    private String firstName;

    private String lastName;

    private String givenName;

    private String birthYear;

    private String birthMonth;

    private String birthDay;

    private String birthCountry;

    private String birthState;

    private String birthCity;

    private String deathYear;

    private String deathMonth;

    private String deathDay;

    private String deathCountry;

    private String deathState;

    private String deathCity;

    private String height;

    private String weight;

    private String bats;

    private String throwStats;

    private String debut;

    private String finalGame;

    private String retroId;

    private String bbrefId;

    // Optional: Add default values for some fields, if needed
    public PlayerCreateDTO() {
        this.height = "Unknown"; // default value for height if not provided
        this.weight = "Unknown"; // default value for weight if not provided
    }
}