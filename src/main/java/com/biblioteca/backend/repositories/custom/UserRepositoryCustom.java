package com.biblioteca.backend.repositories.custom;

import java.util.List;

import com.biblioteca.backend.entities.User;

public interface UserRepositoryCustom {
    List<User> findUserByUsernameLikeERuolo(String username, String ruolo);
}
