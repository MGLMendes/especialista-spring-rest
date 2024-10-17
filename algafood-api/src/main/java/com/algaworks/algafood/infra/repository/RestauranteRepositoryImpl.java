package com.algaworks.algafood.infra.repository;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepositoryQueries;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Restaurante> criteriaQuery = criteriaBuilder.createQuery(Restaurante.class);

        Root<Restaurante> root = criteriaQuery.from(Restaurante.class);// JPQl: from Restaurante

        Predicate nomePredicate = criteriaBuilder.like(
                root.get("nome"), // atributo classe Restaurante
                "%" + nome + "%"
        );

        Predicate taxaInicialPredicate = criteriaBuilder.greaterThanOrEqualTo(
                root.get("taxaFrete"),
                taxaFreteInicial
        );

        Predicate taxaFinalPredicate = criteriaBuilder.lessThanOrEqualTo(
                root.get("taxaFrete"),
                taxaFreteFinal
        );

        criteriaQuery.where(nomePredicate, taxaInicialPredicate, taxaFinalPredicate);

        TypedQuery<Restaurante> query = entityManager.createQuery(criteriaQuery);

        return query.getResultList();
    }
}
