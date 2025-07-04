package com.app.playerservicejava.model;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
public class PlayerDto {
    @NotNull(message = "Player name cannot be null")
    private String firstName;
    @NotNull
    private String lastName;

}
