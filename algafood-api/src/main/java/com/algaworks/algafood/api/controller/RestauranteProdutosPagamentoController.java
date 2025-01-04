package com.algaworks.algafood.api.controller;


import com.algaworks.algafood.api.assembler.ProdutoDTOAssembler;
import com.algaworks.algafood.api.disassembler.ProdutoInputDisassembler;
import com.algaworks.algafood.api.model.dto.ProdutoDTO;
import com.algaworks.algafood.api.model.dto.FormaPagamentoDTO;
import com.algaworks.algafood.api.model.input.ProdutoInput;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.ProdutoService;
import com.algaworks.algafood.domain.service.RestauranteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
@RequiredArgsConstructor
public class RestauranteProdutosPagamentoController {

    private final RestauranteService restauranteService;

    private final ProdutoService produtoService;

    private final ProdutoDTOAssembler produtoDTOAssembler;

    private final ProdutoInputDisassembler produtoInputDisassembler;

    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.buscar(restauranteId);
        return ResponseEntity.ok(produtoDTOAssembler.toCollectionList(restaurante.getProdutos()));
    }

    @GetMapping("/{produtoId}")
    public ResponseEntity<ProdutoDTO> buscar(@PathVariable Long restauranteId,
                                                   @PathVariable Long produtoId) {
        return ResponseEntity.ok(
                produtoDTOAssembler.toModel(produtoService.buscar(restauranteId, produtoId)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoDTO adicionar(@PathVariable Long restauranteId,
                                  @RequestBody @Valid ProdutoInput produtoInput) {
        Restaurante restaurante = restauranteService.buscar(restauranteId);

        Produto produto = produtoInputDisassembler.toDomainObject(produtoInput);
        produto.setRestaurante(restaurante);

        produto = produtoService.salvar(produto);

        return produtoDTOAssembler.toModel(produto);
    }

    @PutMapping("/{produtoId}")
    public ProdutoDTO atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
                                  @RequestBody @Valid ProdutoInput produtoInput) {
        Produto produtoAtual = produtoService.buscar(restauranteId, produtoId);

        produtoInputDisassembler.copyToDomainObject(produtoInput, produtoAtual);

        produtoAtual = produtoService.salvar(produtoAtual);

        return produtoDTOAssembler.toModel(produtoAtual);
    }
}
