package com.biblioteca.backend.repositories.custom;

import java.util.List;

import com.biblioteca.backend.entities.Ordine;

public interface OrdineRepositoryCustom {
    
    List<Ordine> findOrdineByUserIdELibroId(Long userId, Long libroId);

}
