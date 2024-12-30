package com.algaworks.algafood.api.controller;


import com.algaworks.algafood.api.model.CozinhaModel;
import com.algaworks.algafood.api.model.RestauranteModel;
import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.ValidacaoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.ValidationException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restaurantes")
@RequiredArgsConstructor
public class RestauranteController {

    private final RestauranteService restauranteService;

    private final SmartValidator smartValidator;

    @GetMapping
    public ResponseEntity<List<RestauranteModel>> listar() {
        return ResponseEntity.ok(toCollectionList(restauranteService.listar()));
    }

    @GetMapping("/{restauranteId}")
    public ResponseEntity<RestauranteModel> buscar(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.buscar(restauranteId);
        RestauranteModel restauranteModel = toModel(restaurante);
        return ResponseEntity.ok(restauranteModel);

    }

    @PostMapping
    public ResponseEntity<RestauranteModel> salvar(
            @RequestBody @Valid RestauranteInput restauranteInput) {

        return ResponseEntity.status(HttpStatus.CREATED).body(
                toModel(restauranteService.salvar(toDomainObject(restauranteInput))));

    }

    @PutMapping("/{restauranteId}")
    public ResponseEntity<RestauranteModel> atualizar(@PathVariable Long restauranteId, @RequestBody @Valid Restaurante restaurante) {
        return ResponseEntity.ok(
                toModel(restauranteService.atualizar(restauranteId, restaurante)));

    }


    private RestauranteModel toModel(Restaurante restaurante) {
        CozinhaModel cozinhaModel = new CozinhaModel();
        cozinhaModel.setId(restaurante.getCozinha().getId());
        cozinhaModel.setNome(restaurante.getCozinha().getNome());

        RestauranteModel restauranteModel = new RestauranteModel();
        restauranteModel.setId(restaurante.getId());
        restauranteModel.setNome(restaurante.getNome());
        restauranteModel.setTaxaFrete(restaurante.getTaxaFrete());
        restauranteModel.setCozinha(cozinhaModel);
        return restauranteModel;
    }

    private List<RestauranteModel> toCollectionList(List<Restaurante> restaurantes) {
        return restaurantes.stream().map(
                this::toModel  // restaurante -> toModel(restaurante)
        ).collect(Collectors.toList());
    }

    private Restaurante toDomainObject(RestauranteInput restauranteInput) {
        Restaurante restaurante = new Restaurante();
        restaurante.setNome(restauranteInput.getNome());
        restaurante.setTaxaFrete(restauranteInput.getTaxaFrete());

        Cozinha cozinha = new Cozinha();
        cozinha.setId(restauranteInput.getCozinha().getId());

        restaurante.setCozinha(cozinha);
        return restaurante;
    }
}
