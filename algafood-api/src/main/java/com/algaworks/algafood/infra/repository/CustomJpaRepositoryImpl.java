package com.algaworks.algafood.infra.repository;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.CustomJpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;


public class CustomJpaRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID>
        implements CustomJpaRepository<T, ID>{


    private EntityManager entityManager;

    public CustomJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public Optional<T> buscarPrimeiro() {
        var jpql = "from " + getDomainClass().getName();

        T singleResult = entityManager.createQuery(jpql, getDomainClass())
                .setMaxResults(1).getSingleResult();

        return Optional.ofNullable(singleResult);
    }

    @Override
    public void detach(T entity) {
        entityManager.detach(entity);
    }
}
