package com.algaworks.algafood.infra.service.mail;

import com.algaworks.algafood.core.email.EmailProperties;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import freemarker.template.Configuration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
public class FakeEnvioEmailService extends SmtpEnvioEmailService {


    public FakeEnvioEmailService(JavaMailSender mailSender, EmailProperties emailProperties, Configuration freemarkerConfig) {
        super(mailSender, emailProperties, freemarkerConfig);
    }

    @Override
    public void enviar(Mensagem mensagem) {
        String corpo = processarTemplate(mensagem);

        log.info("[FAKE E-MAIL] Para: {}\n{}", mensagem.getDestinatarios(), corpo);
    }
}
