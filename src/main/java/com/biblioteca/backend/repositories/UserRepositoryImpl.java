package com.biblioteca.backend.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.biblioteca.backend.entities.User;
import com.biblioteca.backend.enums.Ruolo;
import com.biblioteca.backend.repositories.custom.UserRepositoryCustom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom
{
    private final EntityManager entityManager;

    @Override
    public List<User> findUserByUsernameLikeERuolo(String username, Ruolo ruolo) 
    {
        CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cQuery = cBuilder.createQuery(User.class);
        Root<User> user = cQuery.from(User.class);

        Predicate predicatoUsername = cBuilder.like(user.get("username"), "%"+username+"%");
        Predicate predicatoRuolo = cBuilder.equal(user.get("ruoli"), ruolo);

        cQuery.where(predicatoUsername, predicatoRuolo);
        TypedQuery<User> query = entityManager.createQuery(cQuery);
        return query.getResultList();
    }
    
}
