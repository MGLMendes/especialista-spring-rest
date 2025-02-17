package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.CidadeDTOAssembler;
import com.algaworks.algafood.api.disassembler.CidadeInputDisassembler;
import com.algaworks.algafood.api.model.dto.CidadeDTO;
import com.algaworks.algafood.api.model.input.CidadeInput;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CidadeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Cidades")
@RestController
@RequestMapping("/cidades")
@RequiredArgsConstructor
public class CidadeController {

    private final CidadeService cidadeService;

    private final CidadeDTOAssembler cidadeDTOAssembler;

    private final CidadeInputDisassembler cidadeInputDisassembler;

    @ApiOperation(
            "Lista as cidades"
    )
    @GetMapping
    public ResponseEntity<List<CidadeDTO>> listar() {
        return ResponseEntity.ok(
                cidadeDTOAssembler.toCollectionList(cidadeService.listar()));
    }

    @ApiOperation(
            "Busca uma cidade por ID"
    )
    @GetMapping("/{cidadeId}")
    public ResponseEntity<CidadeDTO> buscar(@PathVariable Long cidadeId) {
        return ResponseEntity.ok(
                cidadeDTOAssembler.toModel(cidadeService.buscar(cidadeId)));
    }

    @ApiOperation(
            "Cadastra uma cidade"
    )
    @PostMapping
    public ResponseEntity<CidadeDTO> salvar(@RequestBody @Valid CidadeInput cidadeInput) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                cidadeDTOAssembler.toModel(
                        cidadeService.salvar(
                                cidadeInputDisassembler.toDomainObject(cidadeInput))));
    }

    @ApiOperation(
            "Atualiza uma cidade por ID"
    )
    @PutMapping("/{cidadeId}")
    public ResponseEntity<CidadeDTO> atualizar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeInput cidadeInput) {
        Cidade cidadeSalva = cidadeService.buscar(cidadeId);
        cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeSalva);
        return ResponseEntity.ok(
                cidadeDTOAssembler.toModel(cidadeService.salvar(cidadeSalva)));
    }

    @ApiOperation(
            "Exclui uma cidade"
    )
    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long cidadeId) {
        cidadeService.deletar(cidadeId);
    }
}
