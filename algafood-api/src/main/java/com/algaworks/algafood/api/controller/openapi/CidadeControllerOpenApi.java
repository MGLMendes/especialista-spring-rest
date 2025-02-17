package com.algaworks.algafood.api.controller.openapi;

import com.algaworks.algafood.api.handler.Problem;
import com.algaworks.algafood.api.model.dto.CidadeDTO;
import com.algaworks.algafood.api.model.input.CidadeInput;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApi {

    @ApiOperation(
            "Lista as cidades"
    )
    public ResponseEntity<List<CidadeDTO>> listar();

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
    public ResponseEntity<CidadeDTO> buscar(
            @ApiParam(value = "ID de uma cidade", example = "1")
            Long cidadeId);


    @ApiResponses({
            @ApiResponse(
                    code = 201,
                    message = "Cidade cadastrada!"
            )
    })
    @ApiOperation(
            "Cadastra uma cidade"
    )
    public ResponseEntity<CidadeDTO> salvar(
            @ApiParam(name = "corpo", value = "Representação de uma nova cidade")
            CidadeInput cidadeInput);


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
    public ResponseEntity<CidadeDTO> atualizar(
            @ApiParam(value = "ID de uma cidade", example = "1")
            Long cidadeId,
            @ApiParam(name = "corpo", value = "Representação de uma nova cidade")
            CidadeInput cidadeInput);

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
    public void deletar(Long cidadeId);
}
