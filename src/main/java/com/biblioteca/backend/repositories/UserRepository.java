package com.biblioteca.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.backend.entities.User;
import com.biblioteca.backend.repositories.custom.UserRepositoryCustom;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom 
{
    User findUserByUsername(String username);
}
