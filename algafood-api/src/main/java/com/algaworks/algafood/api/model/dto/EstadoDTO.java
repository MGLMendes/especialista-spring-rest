package com.algaworks.algafood.api.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class EstadoDTO extends RepresentationModel<EstadoDTO> {

    @ApiModelProperty(
            example = "1"
    )
    private Long id;
    @ApiModelProperty(
            example = "São Paulo"
    )
    private String nome;
}
