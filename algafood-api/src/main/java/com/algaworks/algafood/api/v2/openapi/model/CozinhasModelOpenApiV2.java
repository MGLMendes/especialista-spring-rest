package com.algaworks.algafood.api.v2.openapi.model;

import com.algaworks.algafood.api.v1.model.dto.CozinhaDTO;
import com.algaworks.algafood.api.v1.openapi.model.PageModelOpenApi;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("CozinhasModel")
@Getter
@Setter
public class CozinhasModelOpenApiV2 {

    private CozinhasEmbeddedModelOpenApi embedded;
    @JsonProperty("_links")
    private Links links;

    private PageModelOpenApi page;
    @Data
    @ApiModel("CozinhasEmbeddedModel")
    public static class CozinhasEmbeddedModelOpenApi {
        private List<CozinhaDTO> cozinhas;
    }

}
