package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.CidadeDTOAssembler;
import com.algaworks.algafood.api.disassembler.CidadeInputDisassembler;
import com.algaworks.algafood.api.model.dto.CidadeDTO;
import com.algaworks.algafood.api.model.input.CidadeInput;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CidadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cidades")
@RequiredArgsConstructor
public class CidadeController {

    private final CidadeService cidadeService;

    private final CidadeDTOAssembler cidadeDTOAssembler;

    private final CidadeInputDisassembler cidadeInputDisassembler;

    @GetMapping
    public ResponseEntity<List<CidadeDTO>> listar() {
        return ResponseEntity.ok(
                cidadeDTOAssembler.toCollectionList(cidadeService.listar()));
    }

    @GetMapping("/{cidadeId}")
    public ResponseEntity<CidadeDTO> buscar(@PathVariable Long cidadeId) {
        return ResponseEntity.ok(
                cidadeDTOAssembler.toModel(cidadeService.buscar(cidadeId)));
    }

    @PostMapping
    public ResponseEntity<CidadeDTO> salvar(@RequestBody @Valid CidadeInput cidade) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                cidadeDTOAssembler.toModel(
                        cidadeService.salvar(
                                cidadeInputDisassembler.toDomainObject(cidade))));
    }

    @PutMapping("/{cidadeId}")
    public ResponseEntity<CidadeDTO> atualizar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeInput cidade) {
        return ResponseEntity.ok(
                cidadeDTOAssembler.toModel(
                        cidadeService.atualizar(cidadeId,
                                cidadeInputDisassembler.toDomainObject(cidade))));
    }

    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long cidadeId) {
        cidadeService.deletar(cidadeId);
    }
}
