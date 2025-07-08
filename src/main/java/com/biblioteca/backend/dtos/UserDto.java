package com.biblioteca.backend.dtos;

import com.biblioteca.backend.entities.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto 
{
    private Long id;
    private String username;
    private String ruolo;


    public UserDto(User user)
    {
        this.id = user.getId();
        this.username = user.getUsername();
        this.ruolo = user.getRuoli().toString();
    }
}
