package di;

import modelo.Cliente;
import notificacao.NotificadorSMS;
import notificacao.NotificarEmail;
import service.AtivacaoClienteService;

public class Main {
    public static void main(String[] args) {
        Cliente joao = new Cliente(
                "Joao",
                "joao@email.com",
                "2123312"
        );

        Cliente maria = new Cliente(
                "maria",
                "maria@email.com",
                "2123312"
        );

        NotificadorSMS notificarEmail = new NotificadorSMS();
        NotificarEmail notificarEmail1 = new NotificarEmail();

        AtivacaoClienteService ativacaoClienteServiceSMS = new AtivacaoClienteService(notificarEmail);
        AtivacaoClienteService ativacaoClienteServiceEmail = new AtivacaoClienteService(notificarEmail1);

        ativacaoClienteServiceSMS.ativar(joao);
        ativacaoClienteServiceEmail.ativar(maria);
    }
}