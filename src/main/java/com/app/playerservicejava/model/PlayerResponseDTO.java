package com.app.playerservicejava.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayerResponseDTO implements Serializable {

    private String firstName;

    private String lastName;

    // Getters and Setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}