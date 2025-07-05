package com.app.playerservicejava.controller;

import com.app.playerservicejava.model.Error;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public abstract class BaseResource {
    private String badRequest="INVALID REQUEST";
    private String internalServerError="SERVER ERROR";
    protected Response ok(Object obj) {
        return Response.status(Status.OK).entity(obj).type(MediaType.APPLICATION_JSON).build();
    }
    protected Response badRequest(Exception e) {
        Error error = new Error();
        error.setErrorName(badRequest);
        error.setErrorMessage(e.getMessage());
        return Response.status(Status.BAD_REQUEST).entity(error).type(MediaType.APPLICATION_JSON).build();
    }
    protected Response serverError(String name, String message) {
        Error error = new Error();
        error.setErrorName(name);
        error.setErrorMessage(message);
        return Response.status(Status.INTERNAL_SERVER_ERROR).entity(error).type(MediaType.APPLICATION_JSON).build();
    }
}