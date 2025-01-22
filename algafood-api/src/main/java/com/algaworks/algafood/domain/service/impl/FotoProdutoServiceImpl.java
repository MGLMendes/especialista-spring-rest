package com.algaworks.algafood.domain.service.impl;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.FotoProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FotoProdutoServiceImpl implements FotoProdutoService {

    private final ProdutoRepository produtoRepository;


    @Transactional
    @Override
    public FotoProduto cadastrarFoto(FotoProduto foto) {

        Long restauranteId = foto.getRestauranteId();
        Long produtoId = foto.getProduto().getId();

        Optional<FotoProduto> fotoById = produtoRepository.findFotoById(restauranteId, produtoId);

        fotoById.ifPresent(produtoRepository::delete);

        return produtoRepository.save(foto);
    }
}
