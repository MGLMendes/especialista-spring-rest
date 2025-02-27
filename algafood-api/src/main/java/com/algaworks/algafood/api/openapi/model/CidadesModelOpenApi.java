package com.algaworks.algafood.api.openapi.model;

import com.algaworks.algafood.api.model.dto.CidadeDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@Data
@ApiModel("CidadesModel")
public class CidadesModelOpenApi {

    private CidadeEmbeddedModelOpenApi embedded;
    @JsonProperty("_links")
    private Links links;
    @Data
    @ApiModel("CidadesEmbeddedModel")
    public static class CidadeEmbeddedModelOpenApi {
        private List<CidadeDTO> cidades;
    }
}
