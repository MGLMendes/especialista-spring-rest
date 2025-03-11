package com.algaworks.algafood.auth.domain.model;

import com.algaworks.algafood.auth.domain.entity.Usuario;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

@Getter
@Setter
public class AuthUser extends User {

    private Long userId;
    private String fullName;

    public AuthUser(Usuario usuario) {
        super(usuario.getEmail(), usuario.getSenha(), Collections.emptyList());
        this.fullName = usuario.getNome();
        this.userId = usuario.getId();
    }
}
