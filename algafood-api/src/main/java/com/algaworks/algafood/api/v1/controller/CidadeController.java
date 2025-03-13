package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.CidadeDTOAssembler;
import com.algaworks.algafood.api.v1.disassembler.CidadeInputDisassembler;
import com.algaworks.algafood.api.v1.model.dto.CidadeDTO;
import com.algaworks.algafood.api.v1.model.input.CidadeInput;
import com.algaworks.algafood.api.v1.openapi.controller.CidadeControllerOpenApi;
import com.algaworks.algafood.core.security.annotations.CheckSecurity;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CidadeService;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;


@RestController
@RequestMapping(path = "/v1/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class CidadeController implements CidadeControllerOpenApi {

    private final CidadeService cidadeService;

    private final CidadeDTOAssembler cidadeDTOAssembler;

    private final CidadeInputDisassembler cidadeInputDisassembler;

    @CheckSecurity.Cidades.PodeConsultar
    @Override
    @GetMapping
    public ResponseEntity<CollectionModel<CidadeDTO>> listar() {

        return ResponseEntity.ok(cidadeDTOAssembler.toCollectionModel(cidadeService.listar()));
    }


    @CheckSecurity.Cidades.PodeConsultar
    @Override
    @GetMapping("/{cidadeId}")
    public ResponseEntity<CidadeDTO> buscar(
            @PathVariable Long cidadeId) {
        Cidade cidade = cidadeService.buscar(cidadeId);
        CidadeDTO cidadeDTO = cidadeDTOAssembler.toModel(cidade);
        return ResponseEntity.ok(cidadeDTO);
    }

    @CheckSecurity.Cidades.PodeEditar
    @Override
    @PostMapping
    public ResponseEntity<CidadeDTO> salvar(
            @RequestBody @Valid CidadeInput cidadeInput) {

        Cidade cidade = cidadeService.salvar(
                cidadeInputDisassembler.toDomainObject(cidadeInput));

        CidadeDTO model = cidadeDTOAssembler.toModel(cidade);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(model.getId()).toUri();


        return ResponseEntity.created(uri).body(model);
    }

    @CheckSecurity.Cidades.PodeEditar
    @Override
    @PutMapping("/{cidadeId}")
    public ResponseEntity<CidadeDTO> atualizar(
            @PathVariable Long cidadeId,
            @RequestBody @Valid CidadeInput cidadeInput) {
        Cidade cidadeSalva = cidadeService.buscar(cidadeId);
        cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeSalva);
        return ResponseEntity.ok(
                cidadeDTOAssembler.toModel(cidadeService.salvar(cidadeSalva)));
    }

    @CheckSecurity.Cidades.PodeEditar
    @Override
    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(
            @ApiParam(value = "ID de uma cidade", example = "1")
            @PathVariable Long cidadeId) {
        cidadeService.deletar(cidadeId);
    }
}
