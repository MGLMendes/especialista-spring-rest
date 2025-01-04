package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Usuario;

import java.util.List;

public interface UsuarioService {

    List<Usuario> listar();

    Usuario buscar(Long usuarioId);

    Usuario salvar(Usuario usuario);

    void deletar(Long usuarioId);

    void alterarSenha(Long usuarioId, String senhaAtual, String novaSenha);

}
