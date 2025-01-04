package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Produto;

public interface ProdutoService {
    

    Produto buscar(Long restauranteId, Long produtoId);

    Produto salvar(Produto produto);



}
