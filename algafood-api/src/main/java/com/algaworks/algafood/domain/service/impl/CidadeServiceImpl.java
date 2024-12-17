package com.algaworks.algafood.domain.service.impl;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
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

import java.util.List;

@Service
@RequiredArgsConstructor
public class CidadeServiceImpl implements CidadeService {

    private static final String MSG_CIDADE_EM_USO
            = "Cidade de código %d não pode ser removida, pois está em uso";

    private static final String MSG_CIDADE_NAO_ENCONTRADA
            = "Não existe um cadastro de cidade com código %d";

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
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format(MSG_CIDADE_NAO_ENCONTRADA, cidadeId)));
    }

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

    @Override
    public Cidade atualizar(Long cidadeId, Cidade cidade) {
        Cidade cidadeSalvo = buscar(cidadeId);

        try {
            Estado estado = estadoService.buscar(cidade.getEstado().getId());

            BeanUtils.copyProperties(cidade, cidadeSalvo, "id");

            cidadeSalvo.setEstado(estado);

            return salvar(cidadeSalvo);
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(
                    String.format(MSG_ESTADO_NAO_ENCONTRADO, cidade.getEstado().getId()),
                    e
            );
        }
    }

    @Override
    public void deletar(Long cidadeId) {
        try {
            cidadeRepository.deleteById(cidadeId);

        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(
                    String.format(MSG_CIDADE_NAO_ENCONTRADA, cidadeId));

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_CIDADE_EM_USO, cidadeId));
        }
    }
}
