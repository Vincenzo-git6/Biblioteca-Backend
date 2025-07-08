package com.biblioteca.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.biblioteca.backend.dtos.UserDto;
import com.biblioteca.backend.entities.User;
import com.biblioteca.backend.enums.Ruolo;
import com.biblioteca.backend.exceptions.UtenteNonEsitenteException;
import com.biblioteca.backend.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService 
{
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    PasswordEncoder passwordEncoder;

    //trova tutti gli utenti
    //solo admin
    public List<UserDto> getAllUsers()
    {
        List<UserDto> users = userRepository.findAll()
                .stream()
                .map(UserDto::new)
                .toList();

        if (users.isEmpty()) 
        {
            throw new UtenteNonEsitenteException("Nessun utente trovato");
        }

        return users;
    }

    //trova un solo utente in base all'id
    //solo admin
    public UserDto getUserById(Long id)
    {
        return userRepository.findById(id)
                .map(UserDto::new)
                .orElseThrow(() -> new UtenteNonEsitenteException("Nessun utente con id "+id+ " trovato"));
    }

    //trova l'utente non dto in base all'username
    //solo admin
    public User findByUsername(String username)
    {
        User user = userRepository.findUserByUsername(username); //non è un Optional quindi non possiamo usare map

        if (user == null) 
        {
            throw new UtenteNonEsitenteException("Non è stato trovato nessun utente con l'username "+username);
        }

        return user;
    }

    //trova l'utente in base all'username
    //solo admin
    public UserDto getUserByUsername(String username)
    {
        User user = userRepository.findUserByUsername(username); //non è un Optional quindi non possiamo usare map

        if (user == null) 
        {
            throw new UtenteNonEsitenteException("Non è stato trovato nessun utente con l'username "+username);
        }

        return new UserDto(user);
    }

    //prende l'utente mandato frontend, imposta il ruolo da null a USER, cripta la password e alla fine salva tutto l'utente
    //solo admin
    @Transactional
    public User registrazioneUser(User user)
    {
        user.setRuoli(Ruolo.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    //cancella l'utente tramite l'id
    //solo admin
    @Transactional
    public void cancellaUser(Long id)
    {
        if (!userRepository.existsById(id)) 
        {
            throw new UtenteNonEsitenteException("Nessun utente trovato con id "+id);
        }

        userRepository.deleteById(id);
    }

    public List<UserDto> getUserByUsernameLikeERuolo(String username, String ruolo)
    {
        return userRepository.findUserByUsernameLikeERuolo(username, ruolo)
                .stream()
                .map(UserDto::new)
                .toList();
    }

}
