package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Restaurante;

import java.util.List;

public interface RestauranteService {

    Restaurante salvar(Restaurante restaurante);

    List<Restaurante> listar();

    Restaurante buscar(Long restauranteId);

    void ativar(Long restauranteId);

    void inativar(Long restauranteId);

    void abrir(Long restauranteId);

    void fechar(Long restauranteId);

    void desvincularFormaPagamento(Long restauranteId, Long formaPagamentoId);

    void vincularFormaPagamento(Long restauranteId, Long formaPagamentoId);
}
