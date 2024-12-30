package com.algaworks.algafood.api.controller;


import com.algaworks.algafood.api.assembler.RestauranteDTOAssembler;
import com.algaworks.algafood.api.disassembler.RestauranteInputDisassembler;
import com.algaworks.algafood.api.model.dto.RestauranteDTO;
import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/restaurantes")
@RequiredArgsConstructor
public class RestauranteController {

    private final RestauranteService restauranteService;

    private final RestauranteDTOAssembler restauranteDTOAssembler;

    private final RestauranteInputDisassembler restauranteInputDisassembler;

    @GetMapping
    public ResponseEntity<List<RestauranteDTO>> listar() {
        return ResponseEntity.ok(restauranteDTOAssembler.toCollectionList(restauranteService.listar()));
    }

    @GetMapping("/{restauranteId}")
    public ResponseEntity<RestauranteDTO> buscar(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.buscar(restauranteId);
        RestauranteDTO restauranteDTO = restauranteDTOAssembler.toModel(restaurante);
        return ResponseEntity.ok(restauranteDTO);

    }

    @PostMapping
    public ResponseEntity<RestauranteDTO> salvar(
            @RequestBody @Valid RestauranteInput restauranteInput) {

        return ResponseEntity.status(HttpStatus.CREATED).body(
                restauranteDTOAssembler.toModel(restauranteService.salvar(restauranteInputDisassembler.toDomainObject(restauranteInput))));

    }

    @PutMapping("/{restauranteId}")
    public ResponseEntity<RestauranteDTO> atualizar(@PathVariable Long restauranteId, @RequestBody @Valid Restaurante restaurante) {
        return ResponseEntity.ok(
                restauranteDTOAssembler.toModel(restauranteService.atualizar(restauranteId, restaurante)));

    }
}
