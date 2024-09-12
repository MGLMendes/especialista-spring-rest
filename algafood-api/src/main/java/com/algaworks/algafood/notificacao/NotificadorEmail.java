package com.algaworks.algafood.notificacao;


import com.algaworks.algafood.modelo.Cliente;
import org.springframework.stereotype.Component;

@Component
public class NotificadorEmail {

    public NotificadorEmail() {
        System.out.println("Construtor chamado");
    }

    public void notificar(Cliente cliente, String mensagem) {
        System.out.printf("Notificando %s atravéz do email %s: %s\n",
                cliente.getNome(), cliente.getEmail(), mensagem);
    }
}
