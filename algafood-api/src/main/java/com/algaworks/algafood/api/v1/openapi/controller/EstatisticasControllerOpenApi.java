package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.controller.EstatisticaController;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiariaDTO;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Estatísticas")
public interface EstatisticasControllerOpenApi {

    @ApiOperation("Consulta estatísticas de vendas diárias")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "restauranteId", value = "ID do restaurante", 
                example = "1", dataType = "int"),
        @ApiImplicitParam(name = "dataCriacaoInicio", value = "Data/hora inicial da criação do pedido",
            example = "2019-12-01T00:00:00Z", dataType = "date-time"),
        @ApiImplicitParam(name = "dataCriacaoFim", value = "Data/hora final da criação do pedido",
            example = "2019-12-02T23:59:59Z", dataType = "date-time")
    })
    ResponseEntity<List<VendaDiariaDTO>> consultarVendasDiarias(
            VendaDiariaFilter filtro,
            
            @ApiParam(value = "Deslocamento de horário a ser considerado na consulta em relação ao UTC",
                defaultValue = "+00:00")
            String timeOffset);

    @ApiOperation(value = "Estatísticas", hidden = true)
    EstatisticaController.EstatisticasModel estatisticas();
}