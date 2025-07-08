package com.biblioteca.backend.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.backend.entities.User;
import com.biblioteca.backend.responses.AuthRequest;
import com.biblioteca.backend.responses.AuthResponse;
import com.biblioteca.backend.security.JwtTokenProvider;
import com.biblioteca.backend.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class AuthController 
{
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;


    @PostMapping("/register")
    public ResponseEntity<?> registrazione(@RequestBody User user)
    {
        try {
            userService.registrazioneUser(user);
            return ResponseEntity.ok(Map.of("message","Registrazione avvenuta con successo!"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody AuthRequest loginRequest)
    {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(), 
                    loginRequest.getPassword()));

            Long idUser = userService.findByUsername(loginRequest.getUsername()).getId();

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtTokenProvider.generateToken(authentication);

            String role = authentication.getAuthorities()
                    .stream()
                    .findFirst()
                    .map(authority -> authority.getAuthority())
                    .orElse("SCONOSCIUTO");


            return ResponseEntity.ok(new AuthResponse(jwt, loginRequest.getUsername(), role, idUser));

        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body("error: "+e.getMessage());
        }
    }

}
