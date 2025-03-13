package com.algaworks.algafood.api.v1.controller;


import com.algaworks.algafood.api.v1.assembler.ProdutoDTOAssembler;
import com.algaworks.algafood.api.v1.disassembler.ProdutoInputDisassembler;
import com.algaworks.algafood.api.v1.links.AlgaLinks;
import com.algaworks.algafood.api.v1.model.dto.ProdutoDTO;
import com.algaworks.algafood.api.v1.model.input.ProdutoInput;
import com.algaworks.algafood.api.v1.openapi.controller.RestauranteProdutoControllerOpenApi;
import com.algaworks.algafood.core.security.annotations.CheckSecurity;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.ProdutoService;
import com.algaworks.algafood.domain.service.RestauranteService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/v1/restaurantes/{restauranteId}/produtos")
@RequiredArgsConstructor
public class RestauranteProdutosController  implements RestauranteProdutoControllerOpenApi {

    private final RestauranteService restauranteService;

    private final ProdutoService produtoService;

    private final ProdutoDTOAssembler produtoDTOAssembler;

    private final ProdutoInputDisassembler produtoInputDisassembler;

    private final AlgaLinks algaLinks;

    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping
    public ResponseEntity<CollectionModel<ProdutoDTO>> listar(@PathVariable Long restauranteId,
                                                              @RequestParam(required = false) Boolean incluirInativos) {
        Restaurante restaurante = restauranteService.buscar(restauranteId);

        List<Produto> todosProdutos = null;

        if (incluirInativos) {
            todosProdutos = produtoService.findTodosByRestaurante(restaurante);
        } else {
            todosProdutos = produtoService.findAtivosByRestaurante(restaurante);
        }

        return ResponseEntity.ok(produtoDTOAssembler.toCollectionModel(todosProdutos)
                .add(algaLinks.linkToProdutos(restauranteId)));
    }

    @CheckSecurity.Restaurantes.PodeConsultar
    @Override
    @GetMapping("/{produtoId}")
    public ResponseEntity<ProdutoDTO> buscar(@PathVariable Long restauranteId,
                                                   @PathVariable Long produtoId) {
        return ResponseEntity.ok(
                produtoDTOAssembler.toModel(produtoService.buscar(restauranteId, produtoId)));
    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
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

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
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
