package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Estado;

import java.util.List;

public interface EstadoService {

    List<Estado> listar();

    Estado buscar(Long estadoId);

    Estado salvar(Estado estado);

    Estado atualizar(Long estadoId, Estado estado);

    void deletar(Long estadoId);
}
