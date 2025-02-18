package com.algaworks.algafood.api.openapi.model;

import com.algaworks.algafood.api.model.dto.CozinhaDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GenericPageModelOpenApi<T> {

    private List<T> content;

    @ApiModelProperty(
            example = "10",
            value = "Quantidade de registros por pagina"
    )
    private Long size;

    @ApiModelProperty(
            example = "50",
            value = "Total de elementos"
    )
    private Long totalElements;

    @ApiModelProperty(
            example = "5",
            value = "Total de páginas"
    )
    private Long totalPages;

    @ApiModelProperty(
            example = "0",
            value = "Número da página"
    )
    private Long number;
}
