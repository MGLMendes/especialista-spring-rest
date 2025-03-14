package com.algaworks.algafood.core.io;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ProtocolResolver;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.util.Base64;


public class Base64ProtocolResolver implements ProtocolResolver,
        ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public Resource resolve(String location, ResourceLoader resourceLoader) {
        if (location.startsWith("base64:")) {
            byte[] decode = Base64.getDecoder().decode(location.substring(7));
            return new ByteArrayResource(decode);
        }
        return null;
    }

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        configurableApplicationContext.addProtocolResolver(this);
    }
}
