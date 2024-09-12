package notificacao;

import modelo.Cliente;

public class NotificarEmail implements Notificador {


    @Override
    public void notificar(Cliente cliente, String mensagem) {
        System.out.printf("Notificando %s atravéz do email %s: %s\n",
                cliente.getNome(), cliente.getEmail(), mensagem);
    }
}
