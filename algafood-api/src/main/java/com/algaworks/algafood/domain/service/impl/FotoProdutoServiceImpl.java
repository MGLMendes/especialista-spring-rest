package com.algaworks.algafood.domain.service.impl;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.FotoProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FotoProdutoServiceImpl implements FotoProdutoService {

    private final ProdutoRepository produtoRepository;


    @Transactional
    @Override
    public FotoProduto cadastrarFoto(FotoProduto foto) {
        return produtoRepository.save(foto);
    }
}
