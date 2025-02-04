package com.algaworks.algafood.infra.service.mail;

import com.algaworks.algafood.core.email.EmailProperties;
import freemarker.template.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Slf4j
public class SandboxEnvioEmailService extends SmtpEnvioEmailService {

    private final EmailProperties emailProperties;

    public SandboxEnvioEmailService(JavaMailSender mailSender,
                                    EmailProperties emailProperties,
                                    Configuration freemarkerConfig) {
        super(mailSender, emailProperties, freemarkerConfig);
        this.emailProperties = emailProperties;
    }

    @Override
    protected MimeMessage criarMimeMessage(Mensagem mensagem) throws MessagingException {
        MimeMessage mimeMessage = super.criarMimeMessage(mensagem);

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

        log.info("Sending email to {}", emailProperties.getSandbox().getDestinatario());

        helper.setTo(emailProperties.getSandbox().getDestinatario());

        return mimeMessage;
    }
}
