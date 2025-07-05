package com.app.playerservicejava.model;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;
@Getter
@Service
public class Recommendation_Request {
    PlayerCreateDTO criteria;
}