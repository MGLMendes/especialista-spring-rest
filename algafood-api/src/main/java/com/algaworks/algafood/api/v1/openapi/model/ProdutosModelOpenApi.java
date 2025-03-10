package com.algaworks.algafood.api.v1.openapi.model;


import com.algaworks.algafood.api.v1.model.dto.ProdutoDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;
@ApiModel("ProdutosModel")
@Data
public class ProdutosModelOpenApi {

    private ProdutosEmbeddedModelOpenApi _embedded;
    private Links _links;
    
    @ApiModel("ProdutosEmbeddedModel")
    @Data
    public static class ProdutosEmbeddedModelOpenApi {
        
        private List<ProdutoDTO> produtos;
        
    }    
}