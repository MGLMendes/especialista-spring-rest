package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.GrupoDTOAssembler;
import com.algaworks.algafood.api.v1.openapi.controller.GrupoControllerOpenApi;
import com.algaworks.algafood.api.v1.disassembler.GrupoInputDisassembler;
import com.algaworks.algafood.api.v1.model.dto.GrupoDTO;
import com.algaworks.algafood.api.v1.model.input.GrupoInput;
import com.algaworks.algafood.core.security.annotations.CheckSecurity;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.GrupoService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/v1/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class GrupoController implements GrupoControllerOpenApi {

    private final GrupoService grupoService;

    private final GrupoDTOAssembler grupoDTOAssembler;

    private final GrupoInputDisassembler grupoInputDisassembler;

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @Override
    @GetMapping
    public ResponseEntity<CollectionModel<GrupoDTO>> listar() {
        return ResponseEntity.ok(
                grupoDTOAssembler.toCollectionModel(grupoService.listar()));
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @Override
    @GetMapping("/{grupoId}")
    public ResponseEntity<GrupoDTO> buscar(@PathVariable Long grupoId) {
        return ResponseEntity.ok(
                grupoDTOAssembler.toModel(grupoService.buscar(grupoId)));
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @Override
    @PostMapping
    public ResponseEntity<GrupoDTO> salvar(@RequestBody @Valid GrupoInput grupoInput) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                grupoDTOAssembler.toModel(
                        grupoService.salvar(
                                grupoInputDisassembler.toDomainObject(grupoInput))));
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @Override
    @PutMapping("/{grupoId}")
    public ResponseEntity<GrupoDTO> atualizar(@PathVariable Long grupoId, @RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupoSalva = grupoService.buscar(grupoId);
        grupoInputDisassembler.copyToDomainObject(grupoInput, grupoSalva);
        return ResponseEntity.ok(
                grupoDTOAssembler.toModel(grupoService.salvar(grupoSalva)));
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @Override
    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long grupoId) {
        grupoService.deletar(grupoId);
    }
}
