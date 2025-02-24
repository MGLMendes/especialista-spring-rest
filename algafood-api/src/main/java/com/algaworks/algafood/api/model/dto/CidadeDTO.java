package com.algaworks.algafood.api.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;


@Getter
@Setter
public class CidadeDTO extends RepresentationModel<CidadeDTO> {

    @ApiModelProperty(
            example = "1"
    )
    private Long id;
    @ApiModelProperty(
            example = "Santos"
    )
    private String nome;

    private EstadoDTO estado;
}
