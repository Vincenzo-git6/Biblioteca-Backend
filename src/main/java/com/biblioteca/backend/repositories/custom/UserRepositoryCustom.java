package com.biblioteca.backend.repositories.custom;

import java.util.List;

import com.biblioteca.backend.entities.User;
import com.biblioteca.backend.enums.Ruolo;

public interface UserRepositoryCustom {
    List<User> findUserByUsernameLikeERuolo(String username, Ruolo ruolo);
}
