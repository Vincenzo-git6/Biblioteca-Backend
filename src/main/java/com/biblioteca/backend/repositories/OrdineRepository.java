package com.biblioteca.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.biblioteca.backend.entities.Ordine;
import com.biblioteca.backend.repositories.custom.OrdineRepositoryCustom;

public interface OrdineRepository extends JpaRepository<Ordine, Long>, OrdineRepositoryCustom
{
    List<Ordine> findByAttivoTrue();
    List<Ordine> findByAttivoFalse();

    @Query("""
        SELECT o 
        FROM Ordine o
        JOIN FETCH o.user u
        JOIN FETCH o.libro l
    """)
    List<Ordine> findOrdiniConLibroEUtente();
}
