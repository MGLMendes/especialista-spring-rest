package com.algaworks.algafood.domain.service.impl;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CidadeService;
import com.algaworks.algafood.domain.service.CidadeService;
import com.algaworks.algafood.domain.service.EstadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CidadeServiceImpl implements CidadeService {

    private final CidadeRepository cidadeRepository;
    private final EstadoService estadoService;

    @Override
    public List<Cidade> listar() {
        return cidadeRepository.findAll();
    }

    @Override
    public Cidade buscar(Long cidadeId) {
        Cidade cidade = cidadeRepository.findById(cidadeId).orElse(null);
        if (cidade == null) {
            throw new EntidadeNaoEncontradaException(
                    String.format(
                            "O cidade de código %d não existe",
                            cidadeId
                    )
            );
        }

        return cidade;
    }

    @Override
    public Cidade salvar(Cidade cidade) {
        Estado estado = estadoService.buscar(cidade.getEstado().getId());
        cidade.setEstado(estado);
        return cidadeRepository.save(cidade);
    }

    @Override
    public Cidade atualizar(Long cidadeId, Cidade cidade) {
        Cidade cidadeSalvo = buscar(cidadeId);

        Estado estado = estadoService.buscar(cidade.getEstado().getId());

        BeanUtils.copyProperties(cidade, cidadeSalvo, "id");

        cidadeSalvo.setEstado(estado);

        return salvar(cidadeSalvo);
    }

    @Override
    public void deletar(Long cidadeId) {
        try {
            Cidade cidade = buscar(cidadeId);
            cidadeRepository.delete(cidade);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format("Cidade de código %d não pode ser removido, pois está em uso",
                    cidadeId));
        }
    }
}
