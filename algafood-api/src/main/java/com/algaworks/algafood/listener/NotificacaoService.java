package com.algaworks.algafood.listener;

import com.algaworks.algafood.annotations.TipoDoNotificador;
import com.algaworks.algafood.enums.NivelUrgencia;
import com.algaworks.algafood.event.ClienteAtivadoEvent;
import com.algaworks.algafood.notificacao.Notificador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class NotificacaoService {

    @Autowired
    @TipoDoNotificador(NivelUrgencia.URGENTE)
    private Notificador notificador;

    @EventListener
    public void clienteAtivadoListener(ClienteAtivadoEvent clienteAtivadoEvent) {
        System.out.println("Cliente " + clienteAtivadoEvent.getCliente().getNome()+ " está ativo! Notificando cliente....");
        notificador.notificar(clienteAtivadoEvent.getCliente(), "Seu cadastro no sistema está ativo!");
    }
}
