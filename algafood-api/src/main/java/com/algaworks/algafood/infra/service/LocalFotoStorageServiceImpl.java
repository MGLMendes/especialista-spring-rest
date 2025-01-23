package com.algaworks.algafood.infra.service;

import com.algaworks.algafood.domain.exception.StorageException;
import com.algaworks.algafood.domain.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.HttpMediaTypeNotAcceptableException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@Service
public class LocalFotoStorageServiceImpl implements FotoStorageService {

    @Value("${algafood.local.storage}")
    private Path diretorioFotos;


    @Override
    public InputStream recuperar(String nomeArquivo) {
        Path arquivoPath = getArquivoPath(nomeArquivo);

        try {
            return Files.newInputStream(arquivoPath);
        } catch (IOException e) {
            throw new StorageException("Não foi possível recuperar a foto", e);
        }
    }

    @Override
    public void armazenar(NovaFoto novaFoto) {
        Path arquivoPath = getArquivoPath(novaFoto.getNomeArquivo());

        try {
            FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(arquivoPath));
        } catch (IOException e) {
            throw new StorageException("Não foi possível armazenar o arquivo!", e);
        }
    }


    @Override
    public void remover(String nomeArquivo) {
        Path arquivoPath = getArquivoPath(nomeArquivo);
        try {
            Files.deleteIfExists(arquivoPath);
        } catch (IOException e) {
            throw new StorageException("Não foi possível excluir arquivo!",e);
        }
    }

    @Override
    public void verificarCompatibilidadeMediaType(MediaType mediaType, List<MediaType> acceptMediaTypes)
            throws HttpMediaTypeNotAcceptableException {

        boolean compativel = acceptMediaTypes.stream()
                .anyMatch(acceptMediaType -> acceptMediaType.isCompatibleWith(mediaType));

        if (!compativel) {
            throw new HttpMediaTypeNotAcceptableException(acceptMediaTypes);
        }
    }

    private Path getArquivoPath(String nomeArquivo) {
        return diretorioFotos.resolve(Path.of(nomeArquivo));
    }


}
