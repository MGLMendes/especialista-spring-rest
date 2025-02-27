package com.algaworks.algafood.api.controller;


import com.algaworks.algafood.api.assembler.RestauranteApenasNomeDTOAssembler;
import com.algaworks.algafood.api.assembler.RestauranteBasicoDTOAssembler;
import com.algaworks.algafood.api.assembler.RestauranteDTOAssembler;
import com.algaworks.algafood.api.disassembler.RestauranteInputDisassembler;
import com.algaworks.algafood.api.model.dto.RestauranteDTO;
import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.api.openapi.controller.RestauranteControllerOpenApi;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class RestauranteController implements RestauranteControllerOpenApi {

    private final RestauranteService restauranteService;

    private final RestauranteDTOAssembler restauranteDTOAssembler;

    private final RestauranteInputDisassembler restauranteInputDisassembler;

    private final RestauranteBasicoDTOAssembler restauranteBasicoDTOAssembler;

    private final RestauranteApenasNomeDTOAssembler restauranteApenasNomeDTOAssembler;

    @Override
    @GetMapping("/projecao")
    public ResponseEntity<?> listarProjecao(@RequestParam(required = false) String projecao) {

        List<Restaurante> restaurantes = restauranteService.listar();

        var restaurantesBasico = ResponseEntity.ok(restauranteBasicoDTOAssembler.toCollectionModel(restaurantes));

        if ("apenas-nome".equalsIgnoreCase(projecao)) {
            return ResponseEntity.ok(restauranteApenasNomeDTOAssembler.toCollectionModel(restaurantes));
        } else if ("completo".equalsIgnoreCase(projecao)) {
           return ResponseEntity.ok(restauranteDTOAssembler.toCollectionModel(restaurantes));
        }

        return restaurantesBasico;
    }


    @Override
    @GetMapping
    public ResponseEntity<CollectionModel<RestauranteDTO>> listar() {
        CollectionModel<RestauranteDTO> collectionList = restauranteDTOAssembler.toCollectionModel(restauranteService.listar());

        return ResponseEntity.ok()
                .body(collectionList);
    }

    @Override
    @GetMapping("/{restauranteId}")
    public ResponseEntity<RestauranteDTO> buscar(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.buscar(restauranteId);
        RestauranteDTO restauranteDTO = restauranteDTOAssembler.toModel(restaurante);
        return ResponseEntity.ok(restauranteDTO);

    }

    @Override
    @PostMapping
    public ResponseEntity<RestauranteDTO> salvar(
            @RequestBody @Valid RestauranteInput restauranteInput) {

        return ResponseEntity.status(HttpStatus.CREATED).body(
                restauranteDTOAssembler.toModel(restauranteService.salvar(restauranteInputDisassembler.toDomainObject(restauranteInput))));

    }

    @Override
    @PutMapping("/{restauranteId}")
    public ResponseEntity<RestauranteDTO> atualizar(@PathVariable Long restauranteId, @RequestBody @Valid RestauranteInput restauranteInput) {

        Restaurante restauranteAtual = restauranteService.buscar(restauranteId);

        restauranteInputDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);

        return ResponseEntity.ok(restauranteDTOAssembler.toModel(restauranteService.salvar(restauranteAtual)));

    }

    @Override
    @PutMapping("/{restauranteId}/ativar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> ativar(@PathVariable Long restauranteId) {
        restauranteService.ativar(restauranteId);
        return ResponseEntity.noContent().build();
    }


    @Override
    @DeleteMapping("/{restauranteId}/inativar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> inativar(@PathVariable Long restauranteId) {
        restauranteService.inativar(restauranteId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping("/{restauranteId}/abrir")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> abrir(@PathVariable Long restauranteId) {
        restauranteService.abrir(restauranteId);
        return ResponseEntity.noContent().build();
    }


    @Override
    @PutMapping("/{restauranteId}/fechar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> fechar(@PathVariable Long restauranteId) {
        restauranteService.fechar(restauranteId);
        return ResponseEntity.noContent().build();
    }


    @Override
    @PutMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> ativarMultiplos(@RequestBody List<Long> restaurantesId) {
        restauranteService.ativar(restaurantesId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @DeleteMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desativarMultiplos(@RequestBody List<Long> restaurantesId) {
        restauranteService.inativar(restaurantesId);
        return ResponseEntity.noContent().build();
    }
}
