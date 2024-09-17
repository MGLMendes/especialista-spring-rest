package com.algaworks.algafood.notificacao;


import com.algaworks.algafood.annotations.TipoDoNotificador;
import com.algaworks.algafood.enums.NivelUrgencia;
import com.algaworks.algafood.modelo.Cliente;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Profile("dev")
@TipoDoNotificador(NivelUrgencia.SEM_URGENCIA)
@Component
public class NotificadorEmailMock implements Notificador {

    @Override
    public void notificar(Cliente cliente, String mensagem) {
        System.out.printf("MOCK: Notificando %s através do email %s: %s\n",
                cliente.getNome(), cliente.getEmail(), mensagem);
    }
}
