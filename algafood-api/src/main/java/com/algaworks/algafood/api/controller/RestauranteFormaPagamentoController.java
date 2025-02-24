package com.algaworks.algafood.api.controller;


import com.algaworks.algafood.api.assembler.FormaPagamentoDTOAssembler;
import com.algaworks.algafood.api.model.dto.FormaPagamentoDTO;
import com.algaworks.algafood.api.openapi.controller.RestauranteFormaPagamentoControllerOpenApi;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/formas-pagamento")
@RequiredArgsConstructor
public class RestauranteFormaPagamentoController implements RestauranteFormaPagamentoControllerOpenApi {

    private final RestauranteService restauranteService;

    private final FormaPagamentoDTOAssembler formaPagamentoDTOAssembler;


    @Override
    @GetMapping
    public ResponseEntity<CollectionModel<FormaPagamentoDTO>> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.buscar(restauranteId);

        return ResponseEntity.ok(formaPagamentoDTOAssembler.toCollectionModel(restaurante.getFormasPagamento()));
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{formaPagamentoId}")
    public void vincular(@PathVariable Long restauranteId,
                                                               @PathVariable Long formaPagamentoId) {
        restauranteService.vincularFormaPagamento(restauranteId, formaPagamentoId);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{formaPagamentoId}")
    public void desvincular(@PathVariable Long restauranteId,
                                                               @PathVariable Long formaPagamentoId) {
        restauranteService.desvincularFormaPagamento(restauranteId, formaPagamentoId);
    }
}
