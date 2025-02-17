package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.CidadeDTOAssembler;
import com.algaworks.algafood.api.disassembler.CidadeInputDisassembler;
import com.algaworks.algafood.api.handler.Problem;
import com.algaworks.algafood.api.model.dto.CidadeDTO;
import com.algaworks.algafood.api.model.input.CidadeInput;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CidadeService;
import io.swagger.annotations.*;
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
    @ApiResponses({
            @ApiResponse(
                    code = 400,
                    message = "ID da cidade inválido",
                    response = Problem.class
            ),
            @ApiResponse(
                    code = 404,
                    message = "Cidade não encontrada",
                    response = Problem.class
            )
    })
    @GetMapping("/{cidadeId}")
    public ResponseEntity<CidadeDTO> buscar(
            @ApiParam(value = "ID de uma cidade", example = "1")
            @PathVariable Long cidadeId) {
        return ResponseEntity.ok(
                cidadeDTOAssembler.toModel(cidadeService.buscar(cidadeId)));
    }


    @ApiResponses({
            @ApiResponse(
                    code = 201,
                    message = "Cidade cadastrada!"
            )
    })
    @ApiOperation(
            "Cadastra uma cidade"
    )
    @PostMapping
    public ResponseEntity<CidadeDTO> salvar(
            @ApiParam(name = "corpo", value = "Representação de uma nova cidade")
            @RequestBody @Valid CidadeInput cidadeInput) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                cidadeDTOAssembler.toModel(
                        cidadeService.salvar(
                                cidadeInputDisassembler.toDomainObject(cidadeInput))));
    }


    @ApiResponses({
            @ApiResponse(
                    code = 404,
                    message = "Cidade não encontrada",
                    response = Problem.class
            ),
            @ApiResponse(
                    code = 200,
                    message = "Cidade atualizada!"
            )
    })
    @ApiOperation(
            "Atualiza uma cidade por ID"
    )
    @PutMapping("/{cidadeId}")
    public ResponseEntity<CidadeDTO> atualizar(
            @ApiParam(value = "ID de uma cidade", example = "1")
            @PathVariable Long cidadeId,
            @ApiParam(name = "corpo", value = "Representação de uma nova cidade")
            @RequestBody @Valid CidadeInput cidadeInput) {
        Cidade cidadeSalva = cidadeService.buscar(cidadeId);
        cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeSalva);
        return ResponseEntity.ok(
                cidadeDTOAssembler.toModel(cidadeService.salvar(cidadeSalva)));
    }

    @ApiResponses({
            @ApiResponse(
                    code = 404,
                    message = "Cidade não encontrada",
                    response = Problem.class
            ),
            @ApiResponse(
                    code = 204,
                    message = "Cidade excluída!"
            )
    })
    @ApiOperation(
            "Exclui uma cidade"
    )
    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(
            @ApiParam(value = "ID de uma cidade", example = "1")
            @PathVariable Long cidadeId) {
        cidadeService.deletar(cidadeId);
    }
}
