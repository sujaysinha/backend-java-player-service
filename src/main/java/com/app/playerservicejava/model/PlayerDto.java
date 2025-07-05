package com.app.playerservicejava.model;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
public class PlayerDto {
    @NotNull(message = "Id cannot be null")
    private String playerId;
    private String firstName;
    private String lastName;
    private String birthYear;
}
