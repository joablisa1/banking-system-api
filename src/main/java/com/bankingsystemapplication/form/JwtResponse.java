package com.bankingsystemapplication.form;

import lombok.*;

import java.util.List;
@Data
@Getter
@Setter

public class JwtResponse {
    private Long id;
    private String token;
    private String type = "Bearer";

    private String username;
    private List<String> roles;

    public JwtResponse() {
    }

    public JwtResponse(String accessToken,String username, List<String> roles) {
        this.token = accessToken;
        this.username = username;
        this.roles = roles;
    }

}
