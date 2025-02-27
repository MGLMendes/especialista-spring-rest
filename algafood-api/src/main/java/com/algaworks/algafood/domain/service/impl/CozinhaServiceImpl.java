package com.algaworks.algafood.domain.service.impl;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CozinhaService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CozinhaServiceImpl implements CozinhaService {

    public static final String MSG_COZINHA_NAO_ENCONTRADA = "Não existe cadastro de cozinha com o código %d";
    public static final String MSG_COZINHA_EM_USO = "Cozinha de código %d não pode ser removida, pois está em uso";
    private final CozinhaRepository cozinhaRepository;

    @Override
    public Cozinha buscar(Long id) {
        return cozinhaRepository.findById(id).orElseThrow(
                () -> new CozinhaNaoEncontradaException(String.format(
                        MSG_COZINHA_NAO_ENCONTRADA, id
                ))
        );
    }

    @Transactional
    @Override
    public Cozinha salvar(Cozinha cozinha) {
        return cozinhaRepository.save(cozinha);
    }

    @Transactional
    @Override
    public void remover(Long cozinhaId) {
        try {
            cozinhaRepository.deleteById(cozinhaId);
            cozinhaRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new CozinhaNaoEncontradaException( cozinhaId);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_COZINHA_EM_USO, cozinhaId));
        }
    }
}
