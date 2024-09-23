package com.algaworks.algafood.infra.repository;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class ListaCidadesMain {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
                .web(WebApplicationType.NONE).run(args);


        CidadeRepository bean = applicationContext.getBean(CidadeRepository.class);
        for (Cidade Cidade : bean.listar()) {
            System.out.println(Cidade.getNome() + " - " + Cidade.getEstado().getNome());
        }
    }
}
