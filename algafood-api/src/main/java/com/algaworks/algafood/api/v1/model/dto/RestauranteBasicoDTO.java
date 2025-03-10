package com.algaworks.algafood.api.v1.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Relation(collectionRelation = "restaurantes")
@Setter
@Getter
public class RestauranteBasicoDTO extends RepresentationModel<RestauranteBasicoDTO> {

    @ApiModelProperty(example = "1")
    private Long id;
    
    @ApiModelProperty(example = "Thai Gourmet")
    private String nome;
    
    @ApiModelProperty(example = "12.00")
    private BigDecimal taxaFrete;
    
    private CozinhaDTO cozinha;
}