package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Cozinha;

public interface CozinhaService {

    Cozinha buscar(Long id);

    Cozinha salvar(Cozinha cozinha);

    void remover(Long cozinhaId);
}
