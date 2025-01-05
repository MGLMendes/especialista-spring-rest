package com.algaworks.algafood.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Email;

@Setter
@Getter
public class UsuarioInput {

    @NotBlank
    private String nome;
    
    @NotBlank
    @Email
    private String email;
}   