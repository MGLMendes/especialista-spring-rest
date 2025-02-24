package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.UsuarioDTOAssembler;
import com.algaworks.algafood.api.model.dto.UsuarioDTO;
import com.algaworks.algafood.api.openapi.controller.RestauranteUsuarioResponsavelControllerOpenApi;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/responsaveis", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class RestauranteUsuarioResponsavelController implements RestauranteUsuarioResponsavelControllerOpenApi {

    private final RestauranteService restauranteService;

    private final UsuarioDTOAssembler usuarioDTOAssembler;

    @GetMapping
    public ResponseEntity<CollectionModel<UsuarioDTO>> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.buscar(restauranteId);

        return ResponseEntity.ok(usuarioDTOAssembler.toCollectionModel(restaurante.getResponsaveis())
                .removeLinks()
                .add(linkTo(methodOn(this.getClass()).listar(restauranteId)).withSelfRel()));
    }

    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        restauranteService.desvincularResponsavel(restauranteId, usuarioId);
    }

    @PutMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        restauranteService.vincularResponsavel(restauranteId, usuarioId);
    }
}