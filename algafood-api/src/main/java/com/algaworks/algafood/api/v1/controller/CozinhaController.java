package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.CozinhaDTOAssembler;
import com.algaworks.algafood.api.v1.disassembler.CozinhaInputDisassembler;
import com.algaworks.algafood.api.v1.model.dto.CozinhaDTO;
import com.algaworks.algafood.api.v1.model.input.CozinhaInput;
import com.algaworks.algafood.api.v1.openapi.controller.CozinhaControllerOpenApi;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CozinhaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(path = "/v1/gastronomias", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class CozinhaController implements CozinhaControllerOpenApi {

//    private static final Logger logger = LoggerFactory.getLogger(CozinhaController.class);

    private final CozinhaRepository cozinhaRepository;

    private final CozinhaService cozinhaService;

    private final CozinhaInputDisassembler cozinhaInputDisassembler;

    private final CozinhaDTOAssembler cozinhaDTOAssembler;

    private final PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

    @Override
    @GetMapping
    public ResponseEntity<PagedModel<CozinhaDTO>> listar(Pageable pageable) {
        log.info("Consultando Cozinhas com page size {}", pageable.getPageSize());

        Page<Cozinha> pageCozinha = cozinhaRepository.findAll(pageable);

        PagedModel<CozinhaDTO> cozinhasDTO = pagedResourcesAssembler.toModel(pageCozinha, cozinhaDTOAssembler);

        return ResponseEntity.ok(cozinhasDTO);
    }

    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<CozinhaDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(
                cozinhaDTOAssembler.toModel(cozinhaService.buscar(id)
        ));
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CozinhaDTO> adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
        return ResponseEntity.ok(
                cozinhaDTOAssembler.toModel(cozinhaService.salvar(cozinhaInputDisassembler.toDomainObject(cozinhaInput))));
    }

    @Override
    @PutMapping("/{cozinhaId}")
    public ResponseEntity<CozinhaDTO> atualizar(@PathVariable Long cozinhaId, @RequestBody @Valid CozinhaInput cozinhaInput) {
        Cozinha cozinhaSalva = cozinhaService.buscar(cozinhaId);
        cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaSalva);
        return ResponseEntity.ok(
                cozinhaDTOAssembler.toModel(
                        cozinhaService.salvar(cozinhaSalva)));
    }

    @Override
    @DeleteMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cozinhaId) {
        cozinhaService.remover(cozinhaId);
    }
}
