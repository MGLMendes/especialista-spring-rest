package com.algaworks.algafood.api.v1.openapi.model;


import com.algaworks.algafood.api.v1.model.dto.EstadoDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;


@ApiModel("EstadosModel")
@Data
public class EstadosModelOpenApi {

    private EstadosEmbeddedModelOpenApi _embedded;
    private Links _links;
    
    @ApiModel("EstadosEmbeddedModel")
    @Data
    public static class EstadosEmbeddedModelOpenApi {
        
        private List<EstadoDTO> estados;
        
    }
}