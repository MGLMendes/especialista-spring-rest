package com.algaworks.algafood.api.controller;


import com.algaworks.algafood.api.assembler.RestauranteDTOAssembler;
import com.algaworks.algafood.api.disassembler.RestauranteInputDisassembler;
import com.algaworks.algafood.api.model.dto.RestauranteDTO;
import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.api.model.view.RestauranteView;
import com.algaworks.algafood.api.openapi.controller.RestauranteControllerOpenApi;
import com.algaworks.algafood.api.openapi.model.RestauranteBasicoModelOpenApi;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
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

    @ApiOperation(value = "Lista Restaurantes", response = RestauranteBasicoModelOpenApi.class)
    @ApiImplicitParams(
            @ApiImplicitParam(
                    value = "Nome da projeção de pedidos",
                    allowableValues = "apenas-nome",
                    name = "projecao",
                    paramType = "query",
                    type = "string"
            )
    )
    @GetMapping("/projecao")
    public MappingJacksonValue listarProjecao(@RequestParam(required = false) String projecao) {

        List<Restaurante> restaurantes = restauranteService.listar();
        List<RestauranteDTO> restauranteDTO = restauranteDTOAssembler.toCollectionList(restaurantes);
        MappingJacksonValue restauranteWrapper = new MappingJacksonValue(
                restauranteDTO
        );

        restauranteWrapper.setSerializationView(RestauranteView.Resumo.class);


        if ("apenas-nome".equalsIgnoreCase(projecao)) {
            restauranteWrapper.setSerializationView(RestauranteView.ApenasNome.class);
        } else if ("completo".equalsIgnoreCase(projecao)) {
            restauranteWrapper.setSerializationView(null);
        }
        return restauranteWrapper;
    }


    @GetMapping
    public ResponseEntity<List<RestauranteDTO>> listar() {
        List<RestauranteDTO> collectionList = restauranteDTOAssembler.toCollectionList(restauranteService.listar());

        return ResponseEntity.ok()
                .body(collectionList);
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
    public ResponseEntity<RestauranteDTO> atualizar(@PathVariable Long restauranteId, @RequestBody @Valid RestauranteInput restauranteInput) {

        Restaurante restauranteAtual = restauranteService.buscar(restauranteId);

        restauranteInputDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);

        return ResponseEntity.ok(restauranteDTOAssembler.toModel(restauranteService.salvar(restauranteAtual)));

    }

    @PutMapping("/{restauranteId}/ativar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativar(@PathVariable Long restauranteId) {
        restauranteService.ativar(restauranteId);
    }


    @DeleteMapping("/{restauranteId}/inativar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativar(@PathVariable Long restauranteId) {
        restauranteService.inativar(restauranteId);
    }

    @PutMapping("/{restauranteId}/abrir")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void abrir(@PathVariable Long restauranteId) {
        restauranteService.abrir(restauranteId);
    }


    @PutMapping("/{restauranteId}/fechar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void fechar(@PathVariable Long restauranteId) {
        restauranteService.fechar(restauranteId);
    }


    @PutMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativarMultiplos(@RequestBody List<Long> restaurantesId) {
        restauranteService.ativar(restaurantesId);
    }

    @DeleteMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desativarMultiplos(@RequestBody List<Long> restaurantesId) {
        restauranteService.inativar(restaurantesId);
    }
}
