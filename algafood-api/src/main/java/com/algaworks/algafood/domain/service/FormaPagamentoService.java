package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.FormaPagamento;

import java.time.OffsetDateTime;
import java.util.List;

public interface FormaPagamentoService {

    OffsetDateTime getDataUltimaAtualizacao();

    List<FormaPagamento> todasFormasPagamento();

    FormaPagamento buscar(Long id);

    FormaPagamento salvar(FormaPagamento formaPagamento);

    void remover(Long formaPagamentoId);
}
