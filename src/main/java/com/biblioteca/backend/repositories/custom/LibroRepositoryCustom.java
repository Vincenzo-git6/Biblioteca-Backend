package com.biblioteca.backend.repositories.custom;

import java.util.List;

import com.biblioteca.backend.entities.Libro;

public interface LibroRepositoryCustom {
    List<Libro> findLibriByAutoreETitolo(String autore, String titolo);

    
}
