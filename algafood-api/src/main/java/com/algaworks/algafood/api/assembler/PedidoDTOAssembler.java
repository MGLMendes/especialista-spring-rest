package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.*;
import com.algaworks.algafood.api.model.dto.PedidoDTO;
import com.algaworks.algafood.domain.model.Pedido;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PedidoDTOAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoDTO> {

    private final ModelMapper modelMapper;

    public PedidoDTOAssembler(ModelMapper modelMapper) {
        super(PedidoController.class, PedidoDTO.class);
        this.modelMapper = modelMapper;
    }

    @Override
    public PedidoDTO toModel(Pedido pedido) {
        PedidoDTO PedidoDTO = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, PedidoDTO);

        PedidoDTO.add(linkTo(PedidoController.class).withRel("pedidos"));

        PedidoDTO.getRestaurante().add(linkTo(methodOn(RestauranteController.class)
                .buscar(pedido.getRestaurante().getId())).withSelfRel());

        PedidoDTO.getCliente().add(linkTo(methodOn(UsuarioController.class)
                .buscar(pedido.getCliente().getId())).withSelfRel());

        // Passamos null no segundo argumento, porque é indiferente para a
        // construção da URL do recurso de forma de pagamento
        PedidoDTO.getFormaPagamento().add(linkTo(methodOn(FormaPagamentoController.class)
                .buscar(pedido.getFormaPagamento().getId(), null)).withSelfRel());

        PedidoDTO.getEnderecoEntrega().getCidade().add(linkTo(methodOn(CidadeController.class)
                .buscar(pedido.getEnderecoEntrega().getCidade().getId())).withSelfRel());

        PedidoDTO.getItens().forEach(item -> {
            item.add(linkTo(methodOn(RestauranteProdutosController.class)
                    .buscar(PedidoDTO.getRestaurante().getId(), item.getProdutoId()))
                    .withRel("produto"));
        });

        return PedidoDTO;
    }

}
