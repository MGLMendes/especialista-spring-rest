package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Grupo;

import java.util.List;

public interface GrupoService {

    List<Grupo> listar();

    Grupo buscar(Long cidadeId);

    Grupo salvar(Grupo cidade);

    void deletar(Long cidadeId);
}
