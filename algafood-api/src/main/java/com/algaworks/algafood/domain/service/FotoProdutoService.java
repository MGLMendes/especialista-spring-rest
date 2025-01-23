package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.FotoProduto;

import java.io.InputStream;

public interface FotoProdutoService {

    FotoProduto buscar(Long restauranteId, Long produtoId);


    FotoProduto cadastrarFoto(FotoProduto foto, InputStream dadosArquivo);
}
