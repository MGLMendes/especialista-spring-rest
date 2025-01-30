package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface PedidoService {

    Page<Pedido> listarTodos(Specification<Pedido> specification, Pageable pageable);

    Pedido buscar(String pedidoId);

    Pedido emitir(Pedido pedido);

}
