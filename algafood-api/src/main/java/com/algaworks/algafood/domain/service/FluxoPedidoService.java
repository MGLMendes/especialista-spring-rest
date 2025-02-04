package com.algaworks.algafood.domain.service;

public interface FluxoPedidoService {

    void confirmar(String codigoPedido);

    void cancelar(String codigoPedido);

    void entregar(String codigoPedido);
}
