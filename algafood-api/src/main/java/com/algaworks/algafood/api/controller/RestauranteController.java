package com.algaworks.algafood.api.controller;


import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.ValidacaoException;
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

@RestController
@RequestMapping("/restaurantes")
@RequiredArgsConstructor
public class RestauranteController {

    private final RestauranteService restauranteService;

    private final SmartValidator smartValidator;

    @GetMapping
    public ResponseEntity<List<Restaurante>> listar() {
        return ResponseEntity.ok(restauranteService.listar());
    }

    @GetMapping("/{restauranteId}")
    public ResponseEntity<Restaurante> buscar(@PathVariable Long restauranteId) {
        return ResponseEntity.ok(restauranteService.buscar(restauranteId));

    }

    @PostMapping
    public ResponseEntity<?> salvar(
            @RequestBody @Valid Restaurante restaurante) {
        return ResponseEntity.status(HttpStatus.CREATED).body(restauranteService.salvar(restaurante));

    }

    @PutMapping("/{restauranteId}")
    public ResponseEntity<?> atualizar(@PathVariable Long restauranteId, @RequestBody @Valid Restaurante restaurante) {
        return ResponseEntity.ok(restauranteService.atualizar(restauranteId, restaurante));

    }

    @PatchMapping("/{restauranteId}")
    public ResponseEntity<?> atualizarParcial(@PathVariable Long restauranteId,
                                              @RequestBody Map<String, Object> campos,
                                              HttpServletRequest request) {
        try {
            Restaurante restauranteSalvo = restauranteService.buscar(restauranteId);

            merge(campos, restauranteSalvo, request);
            validate(restauranteSalvo, "restaurante");

            return ResponseEntity.ok(atualizar(restauranteId, restauranteSalvo));
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private void validate(Restaurante restaurante, String objectName) {
        BeanPropertyBindingResult beanPropertyBindingResult = new BeanPropertyBindingResult(
                restaurante, objectName
        );

        smartValidator.validate(
                restaurante,beanPropertyBindingResult
        );

        if (beanPropertyBindingResult.hasErrors()) {
            throw new ValidacaoException(beanPropertyBindingResult);
        }
    }

    private void merge(Map<String, Object> campos, Restaurante restauranteDestino,
                       HttpServletRequest request) {

        ServletServerHttpRequest servletServerHttpRequest = new ServletServerHttpRequest(request);

        try {
            ObjectMapper objectMapper = new ObjectMapper();

            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

            Restaurante restauranteOrigem = objectMapper.convertValue(campos, Restaurante.class);


            campos.forEach(
                    (nomePropriedade, valorPropriedade) -> {
                        Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
                        if (field != null) {
                            field.setAccessible(true);

                            Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
                            ReflectionUtils.setField(field, restauranteDestino, novoValor);
                        }
                    }
            );
        }catch (IllegalArgumentException e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, servletServerHttpRequest);
        }
    }
}
