package com.algaworks.algafood.api.handler;

import lombok.Getter;

@Getter
public enum ProblemType {
    ACESSO_NEGADO("/acesso-negado", "Acesso negado"),
    DADOS_INVALIDOS(
            "Dados Inválidos!",
            "dados-invalidos"),
    ERRO_SISTEMA(
            "Erro no Sistema!",
            "erro-sistema"),
    PARAMETRO_INVALIDO(
            "Parâmetro Inválido!",
            "parametro-invalido"),
    MENSAGEM_INCOMPREENSIVEL(
            "Não foi possível interpretar a mensagem!",
            "mensagem-incompreensivel"),

    RECURSO_NAO_ENCONTRADO(
            "Recurso Não Encontrado!",
            "recurso-nao-encontrado"),
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
