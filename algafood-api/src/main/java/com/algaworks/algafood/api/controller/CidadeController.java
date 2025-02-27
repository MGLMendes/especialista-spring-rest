package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.CidadeDTOAssembler;
import com.algaworks.algafood.api.disassembler.CidadeInputDisassembler;
import com.algaworks.algafood.api.model.dto.CidadeDTO;
import com.algaworks.algafood.api.model.input.CidadeInput;
import com.algaworks.algafood.api.openapi.controller.CidadeControllerOpenApi;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CidadeService;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping(path = "/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class CidadeController implements CidadeControllerOpenApi {

    private final CidadeService cidadeService;

    private final CidadeDTOAssembler cidadeDTOAssembler;

    private final CidadeInputDisassembler cidadeInputDisassembler;

    @Override
    @GetMapping
    public ResponseEntity<CollectionModel<CidadeDTO>> listar() {

        return ResponseEntity.ok(cidadeDTOAssembler.toCollectionModel(cidadeService.listar()));
    }


    @Override
    @GetMapping("/{cidadeId}")
    public ResponseEntity<CidadeDTO> buscar(
            @PathVariable Long cidadeId) {
        Cidade cidade = cidadeService.buscar(cidadeId);
        CidadeDTO cidadeDTO = cidadeDTOAssembler.toModel(cidade);
        return ResponseEntity.ok(cidadeDTO);
    }


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

    @Override
    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(
            @ApiParam(value = "ID de uma cidade", example = "1")
            @PathVariable Long cidadeId) {
        cidadeService.deletar(cidadeId);
    }
}
