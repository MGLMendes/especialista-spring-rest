package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.dto.PedidoListaDTO;
import com.algaworks.algafood.domain.model.Pedido;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PedidoListaDTOAssembler {

    private final ModelMapper modelMapper;

    public PedidoListaDTO toModel(Pedido pedido) {
        return modelMapper.map(pedido, PedidoListaDTO.class);
    }

    public List<PedidoListaDTO> toCollectionList(List<Pedido> pedidos) {
        return pedidos.stream().map(
                this::toModel  // pedidos -> toModel(pedido)
        ).collect(Collectors.toList());
    }

}
