package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Permissao;

import java.util.List;

public interface GrupoService {

    List<Grupo> listar();

    Grupo buscar(Long grupoId);

    Grupo salvar(Grupo grupo);

    void deletar(Long grupoId);

    void associarPermissao(Long grupoId, Long permissaoId);

    void desassociarPermissao(Long grupoId, Long permissaoId);
}
