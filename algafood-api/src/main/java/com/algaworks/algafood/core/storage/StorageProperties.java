package com.algaworks.algafood.core.storage;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Getter
@Setter
@Component
@ConfigurationProperties("algafood.storage")
public class StorageProperties {

    private Local local = new Local();
    private S3 s3 = new S3();
    private TipoStorage tipo = TipoStorage.LOCAL;


    public enum TipoStorage {
        LOCAL, S3
    }

    @Getter
    @Setter
    public static class Local {
        private Path diretorioFotos;
    }

    @Getter
    @Setter
    public static class S3 {
        private String idChaveAcesso;
        private String idChaveAcessoSecreta;
        private String bucket;
        private String region;
        private String diretorioFotos;
    }
}
