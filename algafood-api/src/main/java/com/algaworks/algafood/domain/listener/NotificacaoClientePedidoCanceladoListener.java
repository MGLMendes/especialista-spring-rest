package com.algaworks.algafood.domain.listener;

import com.algaworks.algafood.domain.event.PedidoCanceladoEvent;
import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class NotificacaoClientePedidoCanceladoListener {

    private final EnvioEmailService emailService;

    @TransactionalEventListener
    public void aoConfirmarPedido(PedidoCanceladoEvent event) {
        var mensagem = EnvioEmailService.Mensagem.builder()
                .assunto(event.getPedido().getRestaurante().getNome() + " Pedido cancelado!")
                .corpo("pedido-cancelado.html")
                .destinatario(event.getPedido().getCliente().getEmail())
                .variavel("pedido", event.getPedido())
                .build();

        emailService.enviar(mensagem);
    }
}
