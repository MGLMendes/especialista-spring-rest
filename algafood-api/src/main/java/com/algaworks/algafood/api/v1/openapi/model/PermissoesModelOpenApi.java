package com.algaworks.algafood.api.v1.openapi.model;

import com.algaworks.algafood.api.v1.model.dto.PermissaoDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("PermissoesModel")
@Data
public class PermissoesModelOpenApi {

    private PermissoesEmbeddedModelOpenApi _embedded;
    private Links _links;
    
    @ApiModel("PermissoesEmbeddedModel")
    @Data
    public static class PermissoesEmbeddedModelOpenApi {
        
        private List<PermissaoDTO> permissoes;
        
    }   
}