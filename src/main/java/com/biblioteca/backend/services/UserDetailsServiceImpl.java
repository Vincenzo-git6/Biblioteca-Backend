package com.biblioteca.backend.services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//import com.biblioteca.backend.entities.User;
import com.biblioteca.backend.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

// Implementazione personalizzata di UserDetailsService per recuperare i dati dell'utente da database
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Metodo chiamato da Spring Security per caricare un utente durante il login.
     * Cerca l'utente nel database tramite l'username fornito.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
    {
        com.biblioteca.backend.entities.User user = userRepository.findUserByUsername(username);

        if (user != null) 
        {
            return buildUserDetails(user.getUsername(), user.getPassword(), user.getRuoli().name());
        }

        throw new UsernameNotFoundException("Utente " + username +" non trovato");
    }

    /**
     * Crea un oggetto UserDetails a partire da username, password e ruolo.
     * Permette a Spring Security di gestire l'autenticazione e le autorizzazioni.
     */
    private UserDetails buildUserDetails(String username, String password, String role) {

        Collection<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(role));

        // Restituisce un oggetto User di Spring Security che contiene credenziali e ruoli
        return new User(username, password, authorities);
    }
}

