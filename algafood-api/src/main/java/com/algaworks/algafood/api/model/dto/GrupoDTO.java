package com.algaworks.algafood.api.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "grupos")
@Setter
@Getter
public class GrupoDTO extends RepresentationModel<GrupoDTO> {

    @ApiModelProperty(example = "Gerente", required = true)
    private Long id;
    @ApiModelProperty(example = "Gerente")
    private String nome;
    
}   