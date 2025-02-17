package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.GrupoDTOAssembler;
import com.algaworks.algafood.api.assembler.PermissaoDTOAssembler;
import com.algaworks.algafood.api.disassembler.GrupoInputDisassembler;
import com.algaworks.algafood.api.model.dto.GrupoDTO;
import com.algaworks.algafood.api.model.dto.PermissaoDTO;
import com.algaworks.algafood.api.model.input.GrupoInput;
import com.algaworks.algafood.api.openapi.controller.GrupoPermissaoControllerOpenApi;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.GrupoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/grupos/{grupoId}/permissoes",
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {

    private final GrupoService grupoService;

    private final PermissaoDTOAssembler permissaoDTOAssembler;


    @GetMapping
    public ResponseEntity<List<PermissaoDTO>> listar(@PathVariable Long grupoId) {
        return ResponseEntity.ok(
                permissaoDTOAssembler.toCollectionList(grupoService.buscar(grupoId).getPermissoes()));
    }

    @DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        grupoService.desassociarPermissao(grupoId, permissaoId);
    }

    @PutMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        grupoService.associarPermissao(grupoId, permissaoId);
    }
}
