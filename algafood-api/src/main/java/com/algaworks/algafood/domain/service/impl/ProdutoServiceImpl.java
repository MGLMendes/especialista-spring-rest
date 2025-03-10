package com.algaworks.algafood.domain.service.impl;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;

    @Override
    public List<Produto> findTodosByRestaurante(Restaurante restaurante) {
        return produtoRepository.findTodosByRestaurante(restaurante);
    }

    @Override
    public List<Produto> findAtivosByRestaurante(Restaurante restaurante) {
        return produtoRepository.findAtivosByRestaurante(restaurante);
    }

    @Override
    public Produto buscar(Long restauranteId, Long produtoId) {
        return produtoRepository.findById(restauranteId, produtoId)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(restauranteId, produtoId));
    }

    @Transactional
    @Override
    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }
    
}
