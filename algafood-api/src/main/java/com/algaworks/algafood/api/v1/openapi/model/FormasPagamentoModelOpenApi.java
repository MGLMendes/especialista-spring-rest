package com.algaworks.algafood.api.v1.openapi.model;

import com.algaworks.algafood.api.v1.model.dto.FormaPagamentoDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("FormasPagamentoModel")
@Data
public class FormasPagamentoModelOpenApi {

    private FormasPagamentoEmbeddedModelOpenApi _embedded;
    private Links _links;
    
    @ApiModel("FormasPagamentoEmbeddedModel")
    @Data
    public static class FormasPagamentoEmbeddedModelOpenApi {
        
        private List<FormaPagamentoDTO> formasPagamento;
        
    }   
}