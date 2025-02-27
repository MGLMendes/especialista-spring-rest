package com.algaworks.algafood.api.v1.disassembler;

import com.algaworks.algafood.api.v1.model.input.PedidoInput;
import com.algaworks.algafood.domain.model.Pedido;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PedidoInputDisassembler {

    private final ModelMapper modelMapper;

    public Pedido toDomainObject(PedidoInput pedidoInput) {
        return modelMapper.map(pedidoInput, Pedido.class);
    }

    public void copyToDomainObject(PedidoInput pedidoInput, Pedido pedido) {
        modelMapper.map(pedidoInput, pedido);
    }
}
