package com.algaworks.algafood.infra.repository;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cozinha;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class CadastroCozinha {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Cozinha> listaCozinha() {
        TypedQuery<Cozinha> fromCozinha = entityManager.createQuery("from Cozinha", Cozinha.class);//JPQL
        return fromCozinha.getResultList();
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
                .web(WebApplicationType.NONE).run(args);


        CadastroCozinha bean = applicationContext.getBean(CadastroCozinha.class);
        for (Cozinha cozinha : bean.listaCozinha()) {
            System.out.println(cozinha.getNome());
        }
    }
}
