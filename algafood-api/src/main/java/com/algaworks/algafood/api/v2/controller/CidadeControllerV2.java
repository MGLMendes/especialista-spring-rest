package com.algaworks.algafood.api.v2.controller;

import com.algaworks.algafood.api.v2.assembler.CidadeDTOAssemblerV2;
import com.algaworks.algafood.api.v2.disassembler.CidadeInputDisassemblerV2;
import com.algaworks.algafood.api.v2.model.dto.CidadeDTOV2;
import com.algaworks.algafood.api.v2.model.input.CidadeInputV2;
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
@RequestMapping(path = "/v1/v2/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class CidadeControllerV2 {

    private final CidadeService cidadeService;

    private final CidadeDTOAssemblerV2 cidadeDTOAssembler;

    private final CidadeInputDisassemblerV2 cidadeInputDisassembler;

    @GetMapping
    public ResponseEntity<CollectionModel<CidadeDTOV2>> listar() {

        return ResponseEntity.ok(cidadeDTOAssembler.toCollectionModel(cidadeService.listar()));
    }


    @GetMapping("/{cidadeId}")
    public ResponseEntity<CidadeDTOV2> buscar(
            @PathVariable Long cidadeId) {
        Cidade cidade = cidadeService.buscar(cidadeId);
        CidadeDTOV2 cidadeDTO = cidadeDTOAssembler.toModel(cidade);
        return ResponseEntity.ok(cidadeDTO);
    }


    @PostMapping
    public ResponseEntity<CidadeDTOV2> salvar(
            @RequestBody @Valid CidadeInputV2 cidadeInput) {

        Cidade cidade = cidadeService.salvar(
                cidadeInputDisassembler.toDomainObject(cidadeInput));

        CidadeDTOV2 model = cidadeDTOAssembler.toModel(cidade);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(model.getIdCidade()).toUri();


        return ResponseEntity.created(uri).body(model);
    }


    @PutMapping("/{cidadeId}")
    public ResponseEntity<CidadeDTOV2> atualizar(
            @PathVariable Long cidadeId,
            @RequestBody @Valid CidadeInputV2 cidadeInput) {
        Cidade cidadeSalva = cidadeService.buscar(cidadeId);
        cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeSalva);
        return ResponseEntity.ok(
                cidadeDTOAssembler.toModel(cidadeService.salvar(cidadeSalva)));
    }

    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(
            @ApiParam(value = "ID de uma cidade", example = "1")
            @PathVariable Long cidadeId) {
        cidadeService.deletar(cidadeId);
    }
}
