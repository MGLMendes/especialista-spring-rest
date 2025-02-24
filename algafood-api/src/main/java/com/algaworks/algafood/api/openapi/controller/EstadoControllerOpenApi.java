package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.handler.Problem;
import com.algaworks.algafood.api.model.dto.EstadoDTO;
import com.algaworks.algafood.api.model.input.EstadoInput;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Estados")
public interface EstadoControllerOpenApi {

    @ApiOperation("Lista os estados")
    ResponseEntity<CollectionModel<EstadoDTO>> listar();

    @ApiOperation("Busca um estado por ID")
    @ApiResponses({
        @ApiResponse(code = 400, message = "ID do estado inválido", response = Problem.class),
        @ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
    })
    ResponseEntity<EstadoDTO> buscar(
            @ApiParam(value = "ID de um estado", example = "1", required = true)
            Long estadoId);

    @ApiOperation("Cadastra um estado")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Estado cadastrado"),
    })
    ResponseEntity<EstadoDTO> salvar(
            @ApiParam(name = "corpo", value = "Representação de um novo estado", required = true)
            EstadoInput estadoInput);

    @ApiOperation("Atualiza um estado por ID")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Estado atualizado"),
        @ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
    })
    ResponseEntity<EstadoDTO> atualizar(
            @ApiParam(value = "ID de um estado", example = "1", required = true)
            Long estadoId,
            
            @ApiParam(name = "corpo", value = "Representação de um estado com os novos dados", required = true)
            EstadoInput estadoInput);

    @ApiOperation("Exclui um estado por ID")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Estado excluído"),
        @ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
    })
    void deletar(
            @ApiParam(value = "ID de um estado", example = "1", required = true)
            Long estadoId);
}     