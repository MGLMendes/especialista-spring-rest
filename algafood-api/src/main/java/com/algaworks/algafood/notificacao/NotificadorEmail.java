package com.algaworks.algafood.notificacao;


import com.algaworks.algafood.modelo.Cliente;
import org.springframework.stereotype.Component;


public class NotificadorEmail implements Notificador {

    private boolean caixaAlta = false;
    private String hostServidorSmtp;

    public NotificadorEmail(String hostServidorSmtp) {
        System.out.println("Construtor chamado");
        this.hostServidorSmtp = hostServidorSmtp;
    }

    @Override
    public void notificar(Cliente cliente, String mensagem) {
        if (caixaAlta) {
            mensagem = mensagem.toUpperCase();
        }
        System.out.printf("Notificando %s atravéz do email %s usando o servidor SMTP %s: %s\n",
                cliente.getNome(), cliente.getEmail(), hostServidorSmtp, mensagem);
    }


    public void setCaixaAlta(boolean caixaAlta) {
        this.caixaAlta = caixaAlta;
    }
}
