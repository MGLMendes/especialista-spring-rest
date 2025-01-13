package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Pedido;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface PedidoService {

    List<Pedido> listarTodos(Specification<Pedido> specification);

    Pedido buscar(String pedidoId);

    Pedido emitir(Pedido pedido);

    void confirmar(String codigoPedido);

    void cancelar(String codigoPedido);

    void entregar(String codigoPedido);
}
