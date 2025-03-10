package com.algaworks.algafood.api.v2.model.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@ApiModel("CidadeInput")
@Getter
@Setter
@Data
public class CidadeInputV2 {

    @ApiModelProperty(
            example = "Santos",
            required = true
    )
    @NotBlank
    private String nomeCidade;

    @ApiModelProperty(example = "1", required = true)
    private Long idEstado;
}
