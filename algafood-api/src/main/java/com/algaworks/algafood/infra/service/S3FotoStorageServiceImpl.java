package com.algaworks.algafood.infra.service;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.exception.StorageException;
import com.algaworks.algafood.domain.service.FotoStorageService;
import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.HttpMediaTypeNotAcceptableException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
@RequiredArgsConstructor
public class S3FotoStorageServiceImpl implements FotoStorageService {

    private final AmazonS3 amazonS3;

    @Override
    public InputStream recuperar(String nomeArquivo) {
        return null;
    }

    @Override
    public void armazenar(NovaFoto novaFoto) {

    }

    @Override
    public void remover(String nomeArquivo) {

    }

    @Override
    public void verificarCompatibilidadeMediaType(MediaType mediaType, List<MediaType> acceptMediaTypes) throws HttpMediaTypeNotAcceptableException {

    }
}
