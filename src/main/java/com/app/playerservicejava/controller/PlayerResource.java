package com.app.playerservicejava.controller;

import com.app.playerservicejava.model.Player;
import com.app.playerservicejava.model.PlayerCreateDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

public interface PlayerResource {
    public ResponseEntity<Player> getPlayerById(String id);
    public Response getPlayers();
    public Response getPlayerTest(@QueryParam("value") String value, @PathVariable("id") String id, @RequestBody PlayerCreateDTO playerCreateDTO);
}