package com.algaworks.algafood.api.handler;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@ApiModel(
        value = "Problema"
)
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Problem {

    @ApiModelProperty(
            example = "400"
    )
    private Integer status;

    @ApiModelProperty(
            example = "https://algafood.com.br/dados-invalidos"
    )
    private String type;

    @ApiModelProperty(
            example = "Dados Inválidos!Dados Inválidos!"
    )
    private String title;

    @ApiModelProperty(
            example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente."
    )

    private String detail;
    @ApiModelProperty(
            example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente."
    )
    private String userMessage;

    @ApiModelProperty(
            example = "2025-02-17T16:17:19.1241395-03:00"
    )
    private OffsetDateTime timestamp;

    @ApiModelProperty(
            value = "Objetos que geraram o erro"
    )
    private List<Object> objects;


    @ApiModel(
            value = "Objeto Problema"
    )
    @Getter
    @Builder
    public static class Object {
        @ApiModelProperty(
                value = "taxaFrete"
        )
        private String name;
        @ApiModelProperty(
                value = "Taxa de frete do restaurante é obrigatória"
        )
        private String userMessage;
    }
}
