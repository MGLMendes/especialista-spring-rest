package com.algaworks.algafood.api.exceptionhandler;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum ProblemType {

    ENTIDADE_NAO_ENCONTRADA(
            "Entidade não encontrada!",
            "entidade-nao-encontrada"),
    ENTIDADE_EM_USO(
            "Entidade em uso!",
            "entidade-em-uso");

    private final String title;
    private final String uri;

    ProblemType(String title, String path) {
        this.uri = "https://algafood.com.br/"+path;
        this.title = title;
    }
}
