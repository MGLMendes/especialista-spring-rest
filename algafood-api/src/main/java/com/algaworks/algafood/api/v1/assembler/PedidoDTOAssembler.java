package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.links.AlgaLinks;
import com.algaworks.algafood.api.v1.model.dto.PedidoDTO;
import com.algaworks.algafood.api.v1.controller.PedidoController;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class PedidoDTOAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoDTO> {

    private final ModelMapper modelMapper;

    private final AlgaLinks algaLinks;

    private final AlgaSecurity algaSecurity;

    public PedidoDTOAssembler(ModelMapper modelMapper, AlgaLinks algaLinks, AlgaSecurity algaSecurity) {
        super(PedidoController.class, PedidoDTO.class);
        this.modelMapper = modelMapper;
        this.algaLinks = algaLinks;
        this.algaSecurity = algaSecurity;
    }

    @Override
    public PedidoDTO toModel(Pedido pedido) {
        PedidoDTO pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoModel);

        // Não usei o método algaSecurity.podePesquisarPedidos(clienteId, restauranteId) aqui,
        // porque na geração do link, não temos o id do cliente e do restaurante,
        // então precisamos saber apenas se a requisição está autenticada e tem o escopo de leitura
        if (algaSecurity.podePesquisarPedidos()) {
            pedidoModel.add(algaLinks.linkToPedidos("pedidos"));
        }

        if (algaSecurity.podeGerenciarPedidos(pedido.getCodigo())) {
            if (pedido.podeSerConfirmado()) {
                pedidoModel.add(algaLinks.linkToConfimacaoPedido(pedido.getCodigo(), "confirmar"));
            }

            if (pedido.podeSerCancelado()) {
                pedidoModel.add(algaLinks.linkToCancelarPedido(pedido.getCodigo(), "cancelar"));
            }

            if (pedido.podeSerEntregue()) {
                pedidoModel.add(algaLinks.linkToEntregarPedido(pedido.getCodigo(), "entregar"));
            }
        }

        if (algaSecurity.podeConsultarRestaurantes()) {
            pedidoModel.getRestaurante().add(
                    algaLinks.linkToRestaurante(pedido.getRestaurante().getId()));
        }

        if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
            pedidoModel.getCliente().add(
                    algaLinks.linkToUsuario(pedido.getCliente().getId()));
        }

        if (algaSecurity.podeConsultarFormasPagamento()) {
            pedidoModel.getFormaPagamento().add(
                    algaLinks.linkToFormaPagamento(pedido.getFormaPagamento().getId()));
        }

        if (algaSecurity.podeConsultarCidades()) {
            pedidoModel.getEnderecoEntrega().getCidade().add(
                    algaLinks.linkToCidade(pedido.getEnderecoEntrega().getCidade().getId()));
        }

        // Quem pode consultar restaurantes, também pode consultar os produtos dos restaurantes
        if (algaSecurity.podeConsultarRestaurantes()) {
            pedidoModel.getItens().forEach(item -> {
                item.add(algaLinks.linkToProduto(
                        pedidoModel.getRestaurante().getId(), item.getProdutoId(), "produto"));
            });
        }

        return pedidoModel;
    }

}
