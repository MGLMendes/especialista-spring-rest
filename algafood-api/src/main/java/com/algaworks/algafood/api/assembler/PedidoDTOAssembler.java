package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.dto.PedidoDTO;
import com.algaworks.algafood.domain.model.Pedido;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PedidoDTOAssembler {

    private final ModelMapper modelMapper;

    public PedidoDTO toModel(Pedido pedido) {
        return modelMapper.map(pedido, PedidoDTO.class);
    }

    public List<PedidoDTO> toCollectionList(List<Pedido> pedidos) {
        return pedidos.stream().map(
                this::toModel  // pedidos -> toModel(pedido)
        ).collect(Collectors.toList());
    }

}
