package com.algaworks.algafood.infra.repository;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cozinha;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class CadastraCozinhaMain {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
                .web(WebApplicationType.NONE).run(args);


        CadastroCozinha bean = applicationContext.getBean(CadastroCozinha.class);
        for (Cozinha cozinha : bean.listaCozinha()) {
            System.out.println(cozinha.getNome());
        }
    }
}
