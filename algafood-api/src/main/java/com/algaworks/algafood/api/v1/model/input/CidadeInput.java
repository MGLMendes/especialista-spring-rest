package com.algaworks.algafood.api.v1.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Data
public class CidadeInput {

    @ApiModelProperty(
            example = "Santos",
            required = true
    )
    @NotBlank
    private String nome;


    @Valid
    @NotNull
    private EstadoIdInput estado;
}
