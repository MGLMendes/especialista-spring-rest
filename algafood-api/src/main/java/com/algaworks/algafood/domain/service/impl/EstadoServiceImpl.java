package com.algaworks.algafood.domain.service.impl;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.EstadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstadoServiceImpl implements EstadoService {

    private final EstadoRepository estadoRepository;

    @Override
    public List<Estado> listar() {
        return estadoRepository.findAll();
    }

    @Override
    public Estado buscar(Long estadoId) {

        Estado estado = estadoRepository.findById(estadoId).orElse(null);

        if (estado == null) {
            throw new EntidadeNaoEncontradaException(
                    String.format(
                            "O estado de código %d não existe",
                            estadoId
                    )
            );
        }

        return estado;
    }

    @Override
    public Estado salvar(Estado estado) {
        return estadoRepository.save(estado);
    }

    @Override
    public Estado atualizar(Long estadoId, Estado estado) {
        Estado estadoSalvo = buscar(estadoId);

        BeanUtils.copyProperties(estado, estadoSalvo, "id");

        return salvar(estadoSalvo);
    }

    @Override
    public void deletar(Long estadoId) {
        try {
            Estado estado = buscar(estadoId);
            estadoRepository.delete(estado);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format("Estado de código %d não pode ser removido, pois está em uso",
                    estadoId));
        }
    }
}
