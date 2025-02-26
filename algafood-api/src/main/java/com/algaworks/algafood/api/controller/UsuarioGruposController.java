package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.GrupoDTOAssembler;
import com.algaworks.algafood.api.disassembler.UsuarioInputDisassembler;
import com.algaworks.algafood.api.model.dto.GrupoDTO;
import com.algaworks.algafood.api.openapi.controller.UsuarioGrupoControllerOpenApi;
import com.algaworks.algafood.domain.service.GrupoService;
import com.algaworks.algafood.domain.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
    @RequestMapping(value = "/usuarios/{usuarioId}/grupos" , produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UsuarioGruposController implements UsuarioGrupoControllerOpenApi {

    private final UsuarioService usuarioService;

    private final GrupoService grupoService;

    private final GrupoDTOAssembler grupoDTOAssembler;

    private final UsuarioInputDisassembler usuarioInputDisassembler;

    @Override
    @GetMapping
    public ResponseEntity<CollectionModel<GrupoDTO>> listar(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(
                grupoDTOAssembler.toCollectionModel(usuarioService.buscar(usuarioId).getGrupos()));
    }

    @Override
    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        usuarioService.desvincularGrupo(usuarioId, grupoId);
    }

    @Override
    @PutMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        usuarioService.vincularGrupo(usuarioId, grupoId);
    }


}
