package com.algaworks.algafood.core.security.annotations;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public @interface CheckSecurity {

    public @interface Cozinhas {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_COZINHAS')")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        public @interface PodeEditar {
        }


        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        public @interface PodeBuscar {
        }
    }
}
