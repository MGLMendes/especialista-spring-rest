package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.CozinhaDTOAssembler;
import com.algaworks.algafood.api.disassembler.CozinhaInputDisassembler;
import com.algaworks.algafood.api.model.dto.CozinhaDTO;
import com.algaworks.algafood.api.model.input.CozinhaInput;
import com.algaworks.algafood.api.openapi.controller.CozinhaControllerOpenApi;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CozinhaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class CozinhaController implements CozinhaControllerOpenApi {

    private final CozinhaRepository cozinhaRepository;

    private final CozinhaService cozinhaService;

    private final CozinhaInputDisassembler cozinhaInputDisassembler;

    private final CozinhaDTOAssembler cozinhaDTOAssembler;

    private final PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

    @GetMapping
    public ResponseEntity<PagedModel<CozinhaDTO>> listar(Pageable pageable) {
        Page<Cozinha> pageCozinha = cozinhaRepository.findAll(pageable);

        PagedModel<CozinhaDTO> cozinhasDTO = pagedResourcesAssembler.toModel(pageCozinha, cozinhaDTOAssembler);

        return ResponseEntity.ok(cozinhasDTO);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CozinhaDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(
                cozinhaDTOAssembler.toModel(cozinhaService.buscar(id)
        ));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CozinhaDTO> adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
        return ResponseEntity.ok(
                cozinhaDTOAssembler.toModel(cozinhaService.salvar(cozinhaInputDisassembler.toDomainObject(cozinhaInput))));
    }

    @PutMapping("/{cozinhaId}")
    public ResponseEntity<CozinhaDTO> atualizar(@PathVariable Long cozinhaId, @RequestBody @Valid CozinhaInput cozinhaInput) {
        Cozinha cozinhaSalva = cozinhaService.buscar(cozinhaId);
        cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaSalva);
        return ResponseEntity.ok(
                cozinhaDTOAssembler.toModel(
                        cozinhaService.salvar(cozinhaSalva)));
    }

    @DeleteMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cozinhaId) {
        cozinhaService.remover(cozinhaId);
    }
}
