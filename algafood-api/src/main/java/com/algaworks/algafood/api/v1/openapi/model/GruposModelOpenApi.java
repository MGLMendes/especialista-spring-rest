package com.algaworks.algafood.api.v1.openapi.model;

import com.algaworks.algafood.api.v1.model.dto.GrupoDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("GruposModel")
@Data
public class GruposModelOpenApi {

    private GruposEmbeddedModelOpenApi _embedded;
    private Links _links;
    
    @ApiModel("GruposEmbeddedModel")
    @Data
    public static class GruposEmbeddedModelOpenApi {
        
        private List<GrupoDTO> grupos;
        
    }   
}