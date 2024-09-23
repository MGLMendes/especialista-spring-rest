package com.algaworks.algafood.infra.repository;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class AlteracaoCozinhaMain {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
                .web(WebApplicationType.NONE).run(args);


        CozinhaRepository bean = applicationContext.getBean(CozinhaRepository.class);
        Cozinha cozinha = new Cozinha();
        cozinha.setNome("Brasileira");
        cozinha.setId(1L);
        bean.salvar(cozinha);

        bean.salvar(cozinha);
    }
}
