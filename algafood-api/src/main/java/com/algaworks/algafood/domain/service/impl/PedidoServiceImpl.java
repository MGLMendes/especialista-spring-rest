package com.algaworks.algafood.domain.service.impl;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.PedidoNaoEncontradoException;
import com.algaworks.algafood.domain.model.*;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final CidadeService cidadeService;
    private final FormaPagamentoService formaPagamentoService;
    private final RestauranteService restauranteService;
    private final UsuarioService usuarioService;
    private final ProdutoService produtoService;

    @Override
    public Page<Pedido> listarTodos(Specification<Pedido> specification, Pageable pageable) {
        return pedidoRepository.findAll(specification, pageable);
    }

    @Override
    public Pedido buscar(String codigoPedido) {
        return pedidoRepository.findByCodigo(codigoPedido).orElseThrow(
                () -> new PedidoNaoEncontradoException(codigoPedido)
        );
    }

    @Transactional
    @Override
    public Pedido emitir(Pedido pedido) {
        validarPedido(pedido);
        validarItens(pedido);

        pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
        pedido.calcularValorTotal();

        return pedidoRepository.save(pedido);
    }

    private void validarPedido(Pedido pedido) {
        Cidade cidade = cidadeService.buscar(pedido.getEnderecoEntrega().getCidade().getId());
        Usuario cliente = usuarioService.buscar(pedido.getCliente().getId());
        Restaurante restaurante = restauranteService.buscar(pedido.getRestaurante().getId());
        FormaPagamento formaPagamento = formaPagamentoService.buscar(pedido.getFormaPagamento().getId());

        pedido.getEnderecoEntrega().setCidade(cidade);
        pedido.setCliente(cliente);
        pedido.setRestaurante(restaurante);
        pedido.setFormaPagamento(formaPagamento);

        if (restaurante.naoAceitaFormaPagamento(formaPagamento)) {
            throw new NegocioException(String.format("Forma de pagamento '%s' não é aceita por esse restaurante.",
                    formaPagamento.getDescricao()));
        }
    }

    private void validarItens(Pedido pedido) {
        pedido.getItens().forEach(item -> {
            Produto produto = produtoService.buscar(
                    pedido.getRestaurante().getId(), item.getProduto().getId());

            item.setPedido(pedido);
            item.setProduto(produto);
            item.setPrecoUnitario(produto.getPreco());
        });
    }
}
