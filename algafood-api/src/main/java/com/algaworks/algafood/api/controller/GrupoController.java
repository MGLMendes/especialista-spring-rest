package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.GrupoDTOAssembler;
import com.algaworks.algafood.api.openapi.controller.GrupoControllerOpenApi;
import com.algaworks.algafood.api.disassembler.GrupoInputDisassembler;
import com.algaworks.algafood.api.model.dto.GrupoDTO;
import com.algaworks.algafood.api.model.input.GrupoInput;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.GrupoService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class GrupoController implements GrupoControllerOpenApi {

    private final GrupoService grupoService;

    private final GrupoDTOAssembler grupoDTOAssembler;

    private final GrupoInputDisassembler grupoInputDisassembler;

    @Override
    @GetMapping
    public ResponseEntity<CollectionModel<GrupoDTO>> listar() {
        return ResponseEntity.ok(
                grupoDTOAssembler.toCollectionModel(grupoService.listar()));
    }

    @Override
    @GetMapping("/{grupoId}")
    public ResponseEntity<GrupoDTO> buscar(@PathVariable Long grupoId) {
        return ResponseEntity.ok(
                grupoDTOAssembler.toModel(grupoService.buscar(grupoId)));
    }

    @Override
    @PostMapping
    public ResponseEntity<GrupoDTO> salvar(@RequestBody @Valid GrupoInput grupoInput) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                grupoDTOAssembler.toModel(
                        grupoService.salvar(
                                grupoInputDisassembler.toDomainObject(grupoInput))));
    }

    @Override
    @PutMapping("/{grupoId}")
    public ResponseEntity<GrupoDTO> atualizar(@PathVariable Long grupoId, @RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupoSalva = grupoService.buscar(grupoId);
        grupoInputDisassembler.copyToDomainObject(grupoInput, grupoSalva);
        return ResponseEntity.ok(
                grupoDTOAssembler.toModel(grupoService.salvar(grupoSalva)));
    }

    @Override
    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long grupoId) {
        grupoService.deletar(grupoId);
    }
}
