package com.algaworks.algafood.core.jackson;

import com.algaworks.algafood.api.v1.model.mixin.CidadeMixin;
import com.algaworks.algafood.api.v1.model.mixin.CozinhaMixin;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Component;

@Component
public class JacsonMixinModule extends SimpleModule {

    public JacsonMixinModule() {
        setMixInAnnotation(Cidade.class, CidadeMixin.class);
        setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
    }
}
