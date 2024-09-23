package com.algaworks.algafood.infra.repository;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class RestauranteRepositoryImpl implements RestauranteRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Restaurante> listar() {
        TypedQuery<Restaurante> fromRestaurante = entityManager.createQuery("from Restaurante", Restaurante.class);//JPQL
        return fromRestaurante.getResultList();
    }

    @Override
    @Transactional
    public Restaurante salvar(Restaurante Restaurante) {
        return entityManager.merge(Restaurante);
    }

    @Override
    public Restaurante buscar(Long id) {
        return entityManager.find(Restaurante.class, id);
    }

    @Override
    @Transactional
    public void remover(Restaurante Restaurante) {
        Restaurante = buscar(Restaurante.getId());
        entityManager.remove(Restaurante);
    }
}
