package com.biblioteca.backend.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.biblioteca.backend.entities.Ordine;
import com.biblioteca.backend.repositories.custom.OrdineRepositoryCustom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrdineRepositoryImpl implements OrdineRepositoryCustom
{
    private final EntityManager entityManager;

    @Override
    public List<Ordine> findOrdineByUserIdELibroId(Long userId, Long libroId) 
    {
        CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Ordine> cQuery = cBuilder.createQuery(Ordine.class);
        Root<Ordine> ordine = cQuery.from(Ordine.class);

        Predicate predicatoUserId = cBuilder.equal(ordine.get("user").get("id"), userId);
        Predicate predicatoLibroId = cBuilder.equal(ordine.get("libro").get("id"), libroId);

        cQuery.where(predicatoUserId, predicatoLibroId);

        TypedQuery<Ordine> query = entityManager.createQuery(cQuery);
        return query.getResultList();  
    }
}
