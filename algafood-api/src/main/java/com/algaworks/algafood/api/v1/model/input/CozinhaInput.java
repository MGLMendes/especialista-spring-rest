package com.algaworks.algafood.api.v1.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CozinhaInput {

    @ApiModelProperty(example = "Brasileira", required = true)
    @NotBlank
    private String nome;
}
