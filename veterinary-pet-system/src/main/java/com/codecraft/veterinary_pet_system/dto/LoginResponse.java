package com.codecraft.veterinary_pet_system.dto;

import lombok.Getter;

@Getter
public class LoginResponse {
    private String token;

    public LoginResponse(String token) {
        this.token = token;
    }

}

