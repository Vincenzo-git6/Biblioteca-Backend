package com.biblioteca.backend.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    private String accessToken;
    private String tokenType = "Bearer";
    private String username;
    private String role;
    private Long idUser;

    public AuthResponse(String accessToken, String username, String role, Long idUser)
    {
        this.accessToken = accessToken;
        this.username = username;
        this.role = role;
        this.idUser = idUser;
    }
}