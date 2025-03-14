package com.algaworks.algafood.domain.listener;

import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class NotificacaoClientePedidoConfirmadoListener {

    private final EnvioEmailService emailService;

    @TransactionalEventListener
    public void aoConfirmarPedido(PedidoConfirmadoEvent event) {
        var mensagem = EnvioEmailService.Mensagem.builder()
                .assunto(event.getPedido().getRestaurante().getNome() + " confirmou o pedido")
                .corpo("emails/pedido-confirmado.html")
                .destinatario(event.getPedido().getCliente().getEmail())
                .variavel("pedido", event.getPedido())
                .build();

        emailService.enviar(mensagem);
    }
}
