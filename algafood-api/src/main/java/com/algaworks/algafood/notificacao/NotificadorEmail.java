package com.algaworks.algafood.notificacao;


import com.algaworks.algafood.annotations.TipoDoNotificador;
import com.algaworks.algafood.config.NotificadorProperties;
import com.algaworks.algafood.enums.NivelUrgencia;
import com.algaworks.algafood.modelo.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@TipoDoNotificador(NivelUrgencia.URGENTE)
@Component
public class NotificadorEmail implements Notificador {

    @Autowired
    private NotificadorProperties notificadorProperties;

    @Override
    public void notificar(Cliente cliente, String mensagem) {
        System.out.println("Host Servidor: " + notificadorProperties.getHostServidor() + " - Port Servidor: " + notificadorProperties.getPortServidor());
        System.out.printf("Notificando %s através do email %s: %s\n",
                cliente.getNome(), cliente.getEmail(), mensagem);
    }
}
