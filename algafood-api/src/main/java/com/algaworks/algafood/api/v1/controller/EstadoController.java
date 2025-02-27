package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.EstadoDTOAssembler;
import com.algaworks.algafood.api.v1.disassembler.EstadoInputDisassembler;
import com.algaworks.algafood.api.v1.model.dto.EstadoDTO;
import com.algaworks.algafood.api.v1.model.input.EstadoInput;
import com.algaworks.algafood.api.v1.openapi.controller.EstadoControllerOpenApi;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.service.EstadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/v1/estados" , produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class EstadoController implements EstadoControllerOpenApi {

    private final EstadoService estadoService;

    private final EstadoDTOAssembler estadoDTOAssembler;

    private final EstadoInputDisassembler estadoInputDisassembler;

    @Override
    @GetMapping
    public ResponseEntity<CollectionModel<EstadoDTO>> listar() {
        return ResponseEntity.ok(
                estadoDTOAssembler.toCollectionModel(estadoService.listar()));
    }

    @Override
    @GetMapping("/{estadoId}")
    public ResponseEntity<EstadoDTO> buscar(@PathVariable Long estadoId) {
        return ResponseEntity.ok(
                estadoDTOAssembler.toModel(estadoService.buscar(estadoId)));
    }

    @Override
    @PostMapping
    public ResponseEntity<EstadoDTO> salvar(@RequestBody @Valid EstadoInput estadoInput) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                estadoDTOAssembler.toModel(
                        estadoService.salvar(estadoInputDisassembler.toDomainObject(estadoInput))));
    }

    @Override
    @PutMapping("/{estadoId}")
    public ResponseEntity<EstadoDTO> atualizar(@PathVariable Long estadoId, @RequestBody @Valid EstadoInput estadoInput) {
        Estado estadoSalvo = estadoService.buscar(estadoId);
        estadoInputDisassembler.copyToDomainObject(estadoInput, estadoSalvo);
        return ResponseEntity.ok(
                estadoDTOAssembler.toModel(estadoService.salvar(estadoSalvo)));
    }

    @Override
    @DeleteMapping("/{estadoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long estadoId) {
        estadoService.deletar(estadoId);

    }
}
