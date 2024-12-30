package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.EstadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/estados")
@RequiredArgsConstructor
public class EstadoController {

    private final EstadoService estadoService;

    @GetMapping
    public ResponseEntity<List<Estado>> listar() {
        return ResponseEntity.ok(estadoService.listar());
    }

    @GetMapping("/{estadoId}")
    public ResponseEntity<Estado> buscar(@PathVariable Long estadoId) {
        return ResponseEntity.ok(estadoService.buscar(estadoId));
    }

    @PostMapping
    public ResponseEntity<Estado> salvar(@RequestBody @Valid Estado estado) {
        return ResponseEntity.status(HttpStatus.CREATED).body(estadoService.salvar(estado));
    }

    @PutMapping("/{estadoId}")
    public ResponseEntity<Estado> atualizar(@PathVariable Long estadoId, @RequestBody @Valid Estado estado) {
            return ResponseEntity.ok(estadoService.atualizar(estadoId, estado));
    }

    @DeleteMapping("/{estadoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long estadoId) {
        estadoService.deletar(estadoId);

    }
}
