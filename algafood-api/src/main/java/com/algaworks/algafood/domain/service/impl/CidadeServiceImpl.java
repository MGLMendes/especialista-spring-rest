package com.algaworks.algafood.domain.service.impl;

import com.algaworks.algafood.domain.exception.*;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CidadeService;
import com.algaworks.algafood.domain.service.CidadeService;
import com.algaworks.algafood.domain.service.EstadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CidadeServiceImpl implements CidadeService {

    private static final String MSG_CIDADE_EM_USO
            = "Cidade de código %d não pode ser removida, pois está em uso";
    

    private static final String MSG_ESTADO_NAO_ENCONTRADO =
            "Não existe um cadastro de estado com código %d";

    private final CidadeRepository cidadeRepository;
    private final EstadoService estadoService;

    @Override
    public List<Cidade> listar() {
        return cidadeRepository.findAll();
    }

    @Override
    public Cidade buscar(Long cidadeId) {
        return cidadeRepository.findById(cidadeId)
                .orElseThrow(() -> new CidadeNaoEncontradaException(cidadeId));
    }

    @Transactional
    @Override
    public Cidade salvar(Cidade cidade) {
        try {
            Long estadoId = cidade.getEstado().getId();

            Estado estado = estadoService.buscar(estadoId);

            cidade.setEstado(estado);

            return cidadeRepository.save(cidade);
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(
                    String.format(MSG_ESTADO_NAO_ENCONTRADO, cidade.getEstado().getId()),
                    e
            );
        }
    }

    @Transactional
    @Override
    public void deletar(Long cidadeId) {
        try {
            cidadeRepository.deleteById(cidadeId);
            cidadeRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new CidadeNaoEncontradaException(cidadeId);

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_CIDADE_EM_USO, cidadeId));
        }
    }
}
