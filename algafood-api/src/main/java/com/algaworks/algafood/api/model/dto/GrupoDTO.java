package com.algaworks.algafood.api.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GrupoDTO {

    @ApiModelProperty(example = "Gerente", required = true)
    private Long id;
    @ApiModelProperty(example = "Gerente")
    private String nome;
    
}   