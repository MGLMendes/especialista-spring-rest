package com.algaworks.algafood.api.v2.controller;

import com.algaworks.algafood.api.v2.assembler.CozinhaDTOAssemblerV2;
import com.algaworks.algafood.api.v2.disassembler.CozinhaInputDisassemblerV2;
import com.algaworks.algafood.api.v2.model.dto.CozinhaDTOV2;
import com.algaworks.algafood.api.v2.model.input.CozinhaInputV2;
import com.algaworks.algafood.api.v2.openapi.controller.CozinhaControllerV2OpenApi;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CozinhaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/v2/gastronomias", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class CozinhaControllerV2 implements CozinhaControllerV2OpenApi {

    private final CozinhaRepository cozinhaRepository;

    private final CozinhaService cozinhaService;

    private final CozinhaInputDisassemblerV2 cozinhaInputDisassembler;

    private final CozinhaDTOAssemblerV2 cozinhaDTOAssembler;

    private final PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

    @GetMapping
    public ResponseEntity<PagedModel<CozinhaDTOV2>> listar(Pageable pageable) {
        Page<Cozinha> pageCozinha = cozinhaRepository.findAll(pageable);

        PagedModel<CozinhaDTOV2> cozinhasDTO = pagedResourcesAssembler.toModel(pageCozinha, cozinhaDTOAssembler);

        return ResponseEntity.ok(cozinhasDTO);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CozinhaDTOV2> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(
                cozinhaDTOAssembler.toModel(cozinhaService.buscar(id)
        ));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CozinhaDTOV2> adicionar(@RequestBody @Valid CozinhaInputV2 cozinhaInput) {
        return ResponseEntity.ok(
                cozinhaDTOAssembler.toModel(cozinhaService.salvar(cozinhaInputDisassembler.toDomainObject(cozinhaInput))));
    }

    @PutMapping("/{cozinhaId}")
    public ResponseEntity<CozinhaDTOV2> atualizar(@PathVariable Long cozinhaId, @RequestBody @Valid CozinhaInputV2 cozinhaInput) {
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
