package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Pedido;

import java.util.List;

public interface PedidoService {

    List<Pedido> listarTodos();

    Pedido buscar(Long pedidoId);

    Pedido emitir(Pedido pedido);
}
