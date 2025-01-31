package com.algaworks.algafood.core.config;

import com.algaworks.algafood.core.email.EmailProperties;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import com.algaworks.algafood.infra.service.mail.FakeEnvioEmailService;
import com.algaworks.algafood.infra.service.mail.SmtpEnvioEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

@RequiredArgsConstructor
@Configuration
public class EmailConfig {

    private final EmailProperties emailProperties;

    @Bean
    public EnvioEmailService envioEmailService(
            JavaMailSender mailSender,
            freemarker.template.Configuration freemarkerConfig
    ) {
        switch (emailProperties.getImpl()) {
            case FAKE:
                return new FakeEnvioEmailService(mailSender, emailProperties, freemarkerConfig);
            case SMTP:
                return new SmtpEnvioEmailService(mailSender, emailProperties, freemarkerConfig);
            default:
                return null;
        }
    }
} 