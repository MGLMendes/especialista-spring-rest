package com.algaworks.algafood.event;

import com.algaworks.algafood.modelo.Cliente;

public class ClienteAtivadoEvent {

    private Cliente cliente;

    public ClienteAtivadoEvent(Cliente cliente) {
        this.cliente = cliente;
    }

    public Cliente getCliente() {
        return cliente;
    }
}
