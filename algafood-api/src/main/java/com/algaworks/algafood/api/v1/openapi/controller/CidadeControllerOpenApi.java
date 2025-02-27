package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.handler.Problem;
import com.algaworks.algafood.api.v1.model.dto.CidadeDTO;
import com.algaworks.algafood.api.v1.model.input.CidadeInput;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApi {

    @ApiOperation(
            "Lista as cidades"
    )
    ResponseEntity<CollectionModel<CidadeDTO>> listar();

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
    ResponseEntity<CidadeDTO> buscar(
            @ApiParam(value = "ID de uma cidade", example = "1", required = true)
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
    ResponseEntity<CidadeDTO> salvar(
            @ApiParam(name = "corpo", value = "Representação de uma nova cidade", required = true)
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
    ResponseEntity<CidadeDTO> atualizar(
            @ApiParam(value = "ID de uma cidade", example = "1", required = true)
            Long cidadeId,

            @ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados", required = true)
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
    void deletar(
            @ApiParam(value = "ID de uma cidade", example = "1", required = true)
            Long cidadeId);
}
