package com.algaworks.algafood.config;

import com.algaworks.algafood.notificacao.NotificadorEmail;
import com.algaworks.algafood.service.AtivacaoClienteService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public AtivacaoClienteService ativacaoClienteService(NotificadorEmail notificadorEmail){
        return new AtivacaoClienteService(notificadorEmail);
    }
}
