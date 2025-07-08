package com.biblioteca.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.backend.entities.Libro;
import com.biblioteca.backend.repositories.custom.LibroRepositoryCustom;


public interface LibroRepository extends JpaRepository<Libro, Long>, LibroRepositoryCustom
{

}
