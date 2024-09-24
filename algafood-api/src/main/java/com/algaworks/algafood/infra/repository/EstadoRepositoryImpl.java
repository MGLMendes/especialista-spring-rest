package com.algaworks.algafood.infra.repository;

import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class EstadoRepositoryImpl implements EstadoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Estado> listar() {
        TypedQuery<Estado> fromEstado = entityManager.createQuery("from Estado", Estado.class);//JPQL
        return fromEstado.getResultList();
    }

    @Override
    @Transactional
    public Estado salvar(Estado estado) {
        return entityManager.merge(estado);
    }

    @Override
    public Estado buscar(Long id) {
        return entityManager.find(Estado.class, id);
    }

    @Override
    @Transactional
    public void remover(Estado estado) {
        estado = buscar(estado.getId());
        entityManager.remove(estado);
    }
}
