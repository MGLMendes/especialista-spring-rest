package com.algaworks.algafood.api.controller;


import com.algaworks.algafood.api.assembler.FormaPagamentoDTOAssembler;
import com.algaworks.algafood.api.assembler.RestauranteDTOAssembler;
import com.algaworks.algafood.api.disassembler.RestauranteInputDisassembler;
import com.algaworks.algafood.api.model.dto.FormaPagamentoDTO;
import com.algaworks.algafood.api.model.dto.RestauranteDTO;
import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/formas-pagamento")
@RequiredArgsConstructor
public class RestauranteFormaPagamentoController {

    private final RestauranteService restauranteService;

    private final FormaPagamentoDTOAssembler formaPagamentoDTOAssembler;


    @GetMapping
    public ResponseEntity<List<FormaPagamentoDTO>> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.buscar(restauranteId);

        return ResponseEntity.ok(formaPagamentoDTOAssembler.toCollectionList(restaurante.getFormasPagamento()));
    }

    @PutMapping("/{formaPagamentoId}")
    public ResponseEntity<List<FormaPagamentoDTO>> vicular(@PathVariable Long restauranteId,
                                                               @PathVariable Long formaPagamentoId) {

        restauranteService.vincularFormaPagamento(restauranteId, formaPagamentoId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{formaPagamentoId}")
    public ResponseEntity<List<FormaPagamentoDTO>> desvincular(@PathVariable Long restauranteId,
                                                               @PathVariable Long formaPagamentoId) {

        restauranteService.desvincularFormaPagamento(restauranteId, formaPagamentoId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
