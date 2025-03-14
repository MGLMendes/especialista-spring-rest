package com.algaworks.algafood.domain.service.impl;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import com.algaworks.algafood.domain.service.GrupoService;
import com.algaworks.algafood.domain.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final GrupoService grupoService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario buscar(Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
    }

    @Transactional
    @Override
    public Usuario salvar(Usuario usuario) {
        usuarioRepository.detach(usuario);
        Optional<Usuario> usuarioPresente = usuarioRepository.findByEmail(usuario.getEmail());

        if (usuarioPresente.isPresent() && !usuarioPresente.get().equals(usuario)) {
            throw new NegocioException(
                    String.format(
                            "Já existe usuário com o email %s",
                            usuario.getEmail()
                    )
            );
        }

        if (usuario.isNovo()) {
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        }

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void alterarSenha(Long usuarioId, String senhaAtual, String novaSenha) {
        Usuario usuario = buscar(usuarioId);

        if (!passwordEncoder.matches(senhaAtual, usuario.getSenha())) {
            throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
        }

        usuario.setSenha(passwordEncoder.encode(novaSenha));
    }

    @Transactional
    @Override
    public void deletar(Long usuarioId) {
        try {
            usuarioRepository.deleteById(usuarioId);
            usuarioRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new UsuarioNaoEncontradoException(usuarioId);
        }
    }

    @Transactional
    @Override
    public void desvincularGrupo(Long usuarioId, Long grupoId) {
        Usuario usuario = buscar(usuarioId);
        Grupo grupo = grupoService.buscar(grupoId);

        usuario.removerGrupo(grupo);
    }

    @Transactional
    @Override
    public void vincularGrupo(Long usuarioId, Long grupoId) {
        Usuario usuario = buscar(usuarioId);
        Grupo grupo = grupoService.buscar(grupoId);

        usuario.adicionarGrupo(grupo);
    }
}
