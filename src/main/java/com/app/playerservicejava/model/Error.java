package com.app.playerservicejava.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Error {
    private String errorName;
    private String errorMessage;
    private String stacktrace;

}