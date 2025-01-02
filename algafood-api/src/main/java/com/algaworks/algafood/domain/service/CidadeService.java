package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Cidade;

import java.util.List;

public interface CidadeService {

    List<Cidade> listar();

    Cidade buscar(Long cidadeId);

    Cidade salvar(Cidade cidade);

    void deletar(Long cidadeId);
}
