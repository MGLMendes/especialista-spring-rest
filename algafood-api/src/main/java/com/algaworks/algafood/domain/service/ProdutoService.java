package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;

import java.util.List;
import java.util.Optional;

public interface ProdutoService {

    List<Produto> findTodosByRestaurante(Restaurante restaurante);

    List<Produto> findAtivosByRestaurante(Restaurante restaurante);

    Produto buscar(Long restauranteId, Long produtoId);

    Produto salvar(Produto produto);



}
