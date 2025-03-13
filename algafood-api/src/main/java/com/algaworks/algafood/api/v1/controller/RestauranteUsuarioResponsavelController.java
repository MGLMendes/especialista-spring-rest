package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.UsuarioDTOAssembler;
import com.algaworks.algafood.api.v1.links.AlgaLinks;
import com.algaworks.algafood.api.v1.model.dto.UsuarioDTO;
import com.algaworks.algafood.api.v1.openapi.controller.RestauranteUsuarioResponsavelControllerOpenApi;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.core.security.annotations.CheckSecurity;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(path = "/v1/restaurantes/{restauranteId}/responsaveis", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class RestauranteUsuarioResponsavelController implements RestauranteUsuarioResponsavelControllerOpenApi {

    private final RestauranteService restauranteService;

    private final UsuarioDTOAssembler usuarioDTOAssembler;

    private final AlgaLinks algaLinks;

    private final AlgaSecurity algaSecurity;

    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping
    public ResponseEntity<CollectionModel<UsuarioDTO>> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.buscar(restauranteId);

        CollectionModel<UsuarioDTO> usuarioDTO = usuarioDTOAssembler.toCollectionModel(restaurante.getResponsaveis())
                .removeLinks();

        usuarioDTO.add(algaLinks.linkToResponsaveisRestaurante(restauranteId));

        if (algaSecurity.podeGerenciarCadastroRestaurantes()) {
            usuarioDTO.add(algaLinks.linkToRestauranteResponsavelAssociacao(restauranteId, "associar"));

            usuarioDTO.getContent().forEach(usuarioModel -> {
                usuarioModel.add(algaLinks.linkToRestauranteResponsavelDesassociacao(
                        restauranteId, usuarioModel.getId(), "desassociar"));
            });
        }

        return ResponseEntity.ok(usuarioDTO);
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        restauranteService.desvincularResponsavel(restauranteId, usuarioId);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @Override
    @PutMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        restauranteService.vincularResponsavel(restauranteId, usuarioId);
        return ResponseEntity.noContent().build();
    }
}