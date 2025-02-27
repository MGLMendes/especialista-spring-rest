package com.algaworks.algafood.infra.repository;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepositoryQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.algaworks.algafood.infra.factory.RestaurantesSpecs.comFreteGratis;
import static com.algaworks.algafood.infra.factory.RestaurantesSpecs.comNomeSemelhante;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    @Lazy
    private RestauranteRepository restauranteRepository;

    @Override
    public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Restaurante> criteriaQuery = criteriaBuilder.createQuery(Restaurante.class);

        Root<Restaurante> root = criteriaQuery.from(Restaurante.class);// JPQl: from Restaurante

        var predicates = new ArrayList<Predicate>();

        if (StringUtils.hasLength(nome)) {
            predicates.add(
                    criteriaBuilder.like(
                            root.get("nome"),
                            "%" + nome + "%"
                    ));
        }

        if  (taxaFreteInicial != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                    root.get("taxaFrete"),
                    taxaFreteInicial
            ));
        }

        if  (taxaFreteFinal != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(
                    root.get("taxaFrete"),
                    taxaFreteFinal
            ));
        }


        criteriaQuery.where(
                predicates.toArray(new Predicate[0])
        );

        TypedQuery<Restaurante> query = entityManager.createQuery(criteriaQuery);

        return query.getResultList();
    }

    @Override
    public List<Restaurante> findComFreteGratis(String nome) {
        return restauranteRepository.findAll(
                comFreteGratis().and(comNomeSemelhante(nome)));
    }
}
