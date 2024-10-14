package com.algaworks.algafood.domain.service.impl;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CozinhaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CozinhaServiceImpl implements CozinhaService {

    private final CozinhaRepository cozinhaRepository;


    @Override
    public Cozinha adicionar(Cozinha cozinha) {
        return cozinhaRepository.salvar(cozinha);
    }
}
