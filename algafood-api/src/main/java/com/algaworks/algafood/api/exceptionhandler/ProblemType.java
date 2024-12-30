package com.algaworks.algafood.api.exceptionhandler;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum ProblemType {

    MENSAGEM_INCOMPREENSIVEL(
            "Não foi possível interpretar a mensagem!",
            "mensagem-incompreensivel"),

    ENTIDADE_NAO_ENCONTRADA(
            "Entidade não encontrada!",
            "entidade-nao-encontrada"),
    ENTIDADE_EM_USO(
            "Entidade em uso!",
            "entidade-em-uso"),

    ERRO_NEGOCIO(
            "Violação de regra de negócio",
            "erro-negocio");

    private final String title;
    private final String uri;

    ProblemType(String title, String path) {
        this.uri = "https://algafood.com.br/"+path;
        this.title = title;
    }
}
