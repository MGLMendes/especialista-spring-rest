package com.algaworks.algafood.api.v1.openapi.model;

import com.algaworks.algafood.api.v1.model.dto.CozinhaDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@ApiModel("Restaurante Básico Model")
public class RestauranteBasicoModelOpenApi {

    @ApiModelProperty(
            example = "1"
    )
    private Long id;

    @ApiModelProperty(
            example = "Thai Gourmet"
    )
    private String nome;

    @ApiModelProperty(
            example = "12.00"
    )
    private BigDecimal taxaFrete;


    private CozinhaDTO cozinha;
}
