package com.algaworks.algafood.api.v1.openapi.model;

import com.algaworks.algafood.api.v1.model.dto.PedidoListaDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;
@Getter
@Setter
@ApiModel("PedidosResumoModel")
public class PedidosResumoModelOpenApi{

    private PedidosResumoEmbeddedModelOpenApi _embedded;
    private Links _links;
    private PageModelOpenApi page;

    @ApiModel("PedidosResumoEmbeddedModel")
    @Data
    public static class PedidosResumoEmbeddedModelOpenApi {

        private List<PedidoListaDTO> pedidos;

    }

} 