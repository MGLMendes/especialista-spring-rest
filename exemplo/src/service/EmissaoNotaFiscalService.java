package service;

import modelo.Cliente;
import modelo.Produto;
import notificacao.Notificador;
import notificacao.NotificarEmail;

public class EmissaoNotaFiscalService {

    private final Notificador notificador;

    public EmissaoNotaFiscalService(Notificador notificador) {
        this.notificador = notificador;
    }

    public void emitir(Cliente cliente, Produto produto) {

        notificador.notificar(cliente, "Nota fical do produto "+ produto.getNome()+ " foi emitida");
    }
}
