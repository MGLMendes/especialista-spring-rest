package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Pedido;

import java.util.List;

public interface PedidoService {

    List<Pedido> listarTodos();

    Pedido buscar(String pedidoId);

    Pedido emitir(Pedido pedido);

    void confirmar(String codigoPedido);

    void cancelar(String codigoPedido);

    void entregar(String codigoPedido);
}
