package com.biblioteca.backend.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.biblioteca.backend.entities.Libro;
import com.biblioteca.backend.repositories.custom.LibroRepositoryCustom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class LibroRepositoryImpl implements LibroRepositoryCustom 
{
     private final EntityManager entityManager;

    @Override
    //usiamo Criteria per creare una query personalizzata, che equivale a scrivere 
    // SELECT * FROM Book WHERE author = 'Mario Rossi' AND title LIKE '%Java%';
    public List<Libro> findLibriByAutoreETitolo(String autore, String titolo)
    {
        CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();  //otteniamo un oggetto CriteriaBuilder che serve da "costruttore di query"
        CriteriaQuery<Libro> cQuery = cBuilder.createQuery(Libro.class);  //crea una nuova query di tipo Libro
        Root<Libro> libro = cQuery.from(Libro.class);  //definisce la tabella di partenza, ovvero Libro

        Predicate predicatoAutore = cBuilder.equal(libro.get("autore"), autore);  //filtro per la query, WHERE
        Predicate predicatoTitolo = cBuilder.like(libro.get("titolo"), "%"+titolo+"%");  //filtro per la query, LIKE

        cQuery.where(predicatoAutore,predicatoTitolo);  //applica i filtri alla query che vengono combinati in automatico con AND da JPA

        TypedQuery<Libro> query = entityManager.createQuery(cQuery);  //trasforma CriteriaQuery<Libro> in una query eseguibile (TypedQuery<Libro>)
        return query.getResultList();  //restituisce una Lista di libri
    }
    
    
}
