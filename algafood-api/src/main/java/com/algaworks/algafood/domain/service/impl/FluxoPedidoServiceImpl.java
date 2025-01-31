package com.algaworks.algafood.domain.service.impl;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import com.algaworks.algafood.domain.service.FluxoPedidoService;
import com.algaworks.algafood.domain.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class FluxoPedidoServiceImpl implements FluxoPedidoService {
    
    private final PedidoService pedidoService;

    private final EnvioEmailService emailService;


    @Transactional
    @Override
    public void confirmar(String codigoPedido) {
        Pedido pedido = pedidoService.buscar(codigoPedido);
        pedido.confirmar();

        var mensagem = EnvioEmailService.Mensagem.builder()
                .assunto(pedido.getRestaurante().getNome() + " confirmou o pedido")
                .corpo("pedido-confirmado.html")
                .destinatario(pedido.getCliente().getEmail())
                .variavel("pedido", pedido)
                .build();

        emailService.enviar(mensagem);
    }

    @Transactional
    @Override
    public void cancelar(String codigoPedido) {
        Pedido pedido = pedidoService.buscar(codigoPedido);
        pedido.cancelar();
    }

    @Transactional
    @Override
    public void entregar(String codigoPedido) {
        Pedido pedido = pedidoService.buscar(codigoPedido);
        pedido.entregar();
    }
}
