package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.CozinhaDTOAssembler;
import com.algaworks.algafood.api.disassembler.CozinhaInputDisassembler;
import com.algaworks.algafood.api.model.dto.CozinhaDTO;
import com.algaworks.algafood.api.model.input.CozinhaInput;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CozinhaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cozinhas")
@RequiredArgsConstructor
public class CozinhaController {

    private final CozinhaRepository cozinhaRepository;

    private final CozinhaService cozinhaService;

    private final CozinhaInputDisassembler cozinhaInputDisassembler;

    private final CozinhaDTOAssembler cozinhaDTOAssembler;

    @GetMapping
    public ResponseEntity<List<CozinhaDTO>> listar() {

        return ResponseEntity.ok(
                cozinhaDTOAssembler.toCollectionList(cozinhaRepository.findAll()));
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
