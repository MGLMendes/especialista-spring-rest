package com.algaworks.algafood.domain.service.impl;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.GrupoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import com.algaworks.algafood.domain.service.GrupoService;
import com.algaworks.algafood.domain.service.PermissaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GrupoServiceImpl implements GrupoService {

    private static final String MSG_GRUPO_EM_USO
            = "Grupo de código %d não pode ser removido, pois está em uso";

    private final GrupoRepository grupoRepository;

    private final PermissaoService permissaoService;

    @Override
    public List<Grupo> listar() {
        return grupoRepository.findAll();
    }

    @Override
    public Grupo buscar(Long grupoId) {
        return grupoRepository.findById(grupoId)
                .orElseThrow(() -> new GrupoNaoEncontradoException(grupoId));
    }

    @Transactional
    @Override
    public Grupo salvar(Grupo grupo) {
        return grupoRepository.save(grupo);
    }

    @Transactional
    @Override
    public void deletar(Long grupoId) {
        try {
            grupoRepository.deleteById(grupoId);
            grupoRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new GrupoNaoEncontradoException(grupoId);

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_GRUPO_EM_USO, grupoId));
        }
    }

    @Transactional
    @Override
    public void associarPermissao(Long grupoId, Long permissaoId) {
        Grupo grupo = buscar(grupoId);
        Permissao permissao = permissaoService.buscar(permissaoId);
        grupo.adicionarPermissao(permissao);
    }

    @Transactional
    @Override
    public void desassociarPermissao(Long grupoId, Long permissaoId) {
        Grupo grupo = buscar(grupoId);
        Permissao permissao = permissaoService.buscar(permissaoId);
        grupo.removerPermissao(permissao);
    }
}
