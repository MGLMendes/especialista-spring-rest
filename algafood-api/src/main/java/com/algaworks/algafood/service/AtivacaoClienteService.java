package com.algaworks.algafood.service;


import com.algaworks.algafood.annotations.TipoDoNotificador;
import com.algaworks.algafood.enums.NivelUrgencia;
import com.algaworks.algafood.modelo.Cliente;
import com.algaworks.algafood.notificacao.Notificador;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

//@Component
public class AtivacaoClienteService {// implements InitializingBean, DisposableBean {

    @TipoDoNotificador(NivelUrgencia.SEM_URGENCIA)
    @Autowired
    private Notificador notificador;

//    @PostConstruct
    public void init() {
        System.out.println("INIT BEAN");
    }

//    @PreDestroy
//    @Override
    public void destroy() {
        System.out.println("DESTROY BEAN");
    }


    public void ativar(Cliente cliente) {
        cliente.ativar();

        notificador.notificar(cliente, "Seu cadastro no sistema está ativo!");

    }

//    @Override
//    public void afterPropertiesSet() throws Exception {
//
//    }


}
