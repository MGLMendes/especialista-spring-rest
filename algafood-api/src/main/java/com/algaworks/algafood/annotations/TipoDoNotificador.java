package com.algaworks.algafood.annotations;


import com.algaworks.algafood.enums.NivelUrgencia;
import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface TipoDoNotificador {

    NivelUrgencia value();
}
