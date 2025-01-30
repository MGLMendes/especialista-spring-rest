package com.algaworks.algafood.core.config;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.FotoStorageService;
import com.algaworks.algafood.infra.service.LocalFotoStorageServiceImpl;
import com.algaworks.algafood.infra.service.S3FotoStorageServiceImpl;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class StorageConfig {

    private final StorageProperties storageProperties;

    @Bean
    public AmazonS3 amazonS3() {
        var credentials = new BasicAWSCredentials(
                storageProperties.getS3().getIdChaveAcesso(),
                storageProperties.getS3().getIdChaveAcessoSecreta());
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(storageProperties.getS3().getRegion())
                .build();
    }

    @Bean
    public FotoStorageService fotoStorageService(
            AmazonS3 amazonS3
    ) {
        if (StorageProperties.TipoStorage.S3.equals(storageProperties.getTipo())) {
            return new S3FotoStorageServiceImpl(amazonS3, storageProperties);
        } else {
            return new LocalFotoStorageServiceImpl(storageProperties);
        }
    }

}
