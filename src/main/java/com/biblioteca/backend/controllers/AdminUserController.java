package com.biblioteca.backend.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.backend.dtos.UserDto;
import com.biblioteca.backend.enums.Ruolo;
import com.biblioteca.backend.exceptions.UtenteNonEsitenteException;
import com.biblioteca.backend.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/user")
@RequiredArgsConstructor
public class AdminUserController 
{
    private final UserService userService;
    
    //viusalizza tutti gli utenti
    @GetMapping("/tutti")
    public List<UserDto> tuttiUsers()
    {
        return userService.getAllUsers();
    }

    //visualizza un utente dall'id
    @GetMapping("/{id}")
    public UserDto singoloUser(@PathVariable Long id)
    {
        return userService.getUserById(id);
    }

    @DeleteMapping("/elimina/{id}")
    public ResponseEntity<?> eliminaUser(@PathVariable Long id)
    {
        userService.cancellaUser(id);
        return ResponseEntity.ok().body(Map.of("message","Utente eliminato"));
    }

    @GetMapping("like")
    public List<UserDto> getUserByUsernameLikeERuolo(@RequestParam String username, @RequestParam String ruolo)
    {
        List<UserDto> users = userService.getUserByUsernameLikeERuolo(username, ruolo);
        if (users.isEmpty()) {
            throw new UtenteNonEsitenteException("Non esiste nessun utente con username simile a : " +username+ " e ruolo: "+ruolo);
        }
        return users;
    }
}
