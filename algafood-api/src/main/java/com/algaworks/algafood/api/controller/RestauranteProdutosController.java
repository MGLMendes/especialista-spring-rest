package com.algaworks.algafood.api.controller;


import com.algaworks.algafood.api.assembler.ProdutoDTOAssembler;
import com.algaworks.algafood.api.disassembler.ProdutoInputDisassembler;
import com.algaworks.algafood.api.model.dto.ProdutoDTO;
import com.algaworks.algafood.api.model.input.ProdutoInput;
import com.algaworks.algafood.api.openapi.controller.RestauranteProdutoControllerOpenApi;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.ProdutoService;
import com.algaworks.algafood.domain.service.RestauranteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
@RequiredArgsConstructor
public class RestauranteProdutosController  implements RestauranteProdutoControllerOpenApi {

    private final RestauranteService restauranteService;

    private final ProdutoService produtoService;

    private final ProdutoRepository produtoRepository;

    private final ProdutoDTOAssembler produtoDTOAssembler;

    private final ProdutoInputDisassembler produtoInputDisassembler;

    @Override
    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> listar(@PathVariable Long restauranteId,
                                                   @RequestParam(required = false) boolean incluirInativos) {
        Restaurante restaurante = restauranteService.buscar(restauranteId);

        List<Produto> findAllByRestaurantes = produtoRepository.findAtivosByRestaurante(restaurante);

        if (Boolean.TRUE.equals(incluirInativos)) {
            findAllByRestaurantes = produtoRepository.findByRestaurante(restaurante);
        }

        return ResponseEntity.ok(produtoDTOAssembler.toCollectionList(findAllByRestaurantes));
    }



    @Override
    @GetMapping("/{produtoId}")
    public ResponseEntity<ProdutoDTO> buscar(@PathVariable Long restauranteId,
                                                   @PathVariable Long produtoId) {
        return ResponseEntity.ok(
                produtoDTOAssembler.toModel(produtoService.buscar(restauranteId, produtoId)));
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProdutoDTO> adicionar(@PathVariable Long restauranteId,
                                  @RequestBody @Valid ProdutoInput produtoInput) {
        Restaurante restaurante = restauranteService.buscar(restauranteId);

        Produto produto = produtoInputDisassembler.toDomainObject(produtoInput);
        produto.setRestaurante(restaurante);

        produto = produtoService.salvar(produto);

        return ResponseEntity.ok(produtoDTOAssembler.toModel(produto));
    }

    @Override
    @PutMapping("/{produtoId}")
    public ResponseEntity<ProdutoDTO> atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
                                  @RequestBody @Valid ProdutoInput produtoInput) {
        Produto produtoAtual = produtoService.buscar(restauranteId, produtoId);

        produtoInputDisassembler.copyToDomainObject(produtoInput, produtoAtual);

        produtoAtual = produtoService.salvar(produtoAtual);

        return ResponseEntity.ok(produtoDTOAssembler.toModel(produtoAtual));
    }
}
