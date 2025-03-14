package com.algaworks.algafood.api.v1.openapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("PageModel")
public class PageModelOpenApi {

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
