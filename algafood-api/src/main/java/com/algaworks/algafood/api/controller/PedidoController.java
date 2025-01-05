package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.PedidoDTOAssembler;
import com.algaworks.algafood.api.assembler.PedidoListaDTOAssembler;
import com.algaworks.algafood.api.model.dto.PedidoDTO;
import com.algaworks.algafood.api.model.dto.PedidoListaDTO;
import com.algaworks.algafood.domain.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;
    
    private final PedidoDTOAssembler pedidoDTOAssembler;

    private final PedidoListaDTOAssembler pedidoListaDTOAssembler;

    @GetMapping
    public ResponseEntity<List<PedidoListaDTO>> listar() {
        return ResponseEntity.ok(
                pedidoListaDTOAssembler.toCollectionList(pedidoService.listarTodos())
        );
    }

    @GetMapping("/{pedidoId}")
    public ResponseEntity<PedidoDTO> buscar(@PathVariable Long pedidoId) {
        return ResponseEntity.ok(
                pedidoDTOAssembler.toModel(pedidoService.buscar(pedidoId))
        );
    }

}
