package com.algaworks.algafood.infra.repository;

import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class FormaPagamentoRepositoryImpl implements FormaPagamentoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<FormaPagamento> listar() {
        TypedQuery<FormaPagamento> fromformaPagamento = entityManager.createQuery("from FormaPagamento", FormaPagamento.class);//JPQL
        return fromformaPagamento.getResultList();
    }

    @Override
    @Transactional
    public FormaPagamento salvar(FormaPagamento formaPagamento) {
        return entityManager.merge(formaPagamento);
    }

    @Override
    public FormaPagamento buscar(Long id) {
        return entityManager.find(FormaPagamento.class, id);
    }

    @Override
    @Transactional
    public void remover(FormaPagamento formaPagamento) {
        formaPagamento = buscar(formaPagamento.getId());
        entityManager.remove(formaPagamento);
    }
}
