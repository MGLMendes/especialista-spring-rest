package com.algaworks.algafood.domain.service;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.MediaType;
import org.springframework.web.HttpMediaTypeNotAcceptableException;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

public interface FotoStorageService {

    InputStream recuperar(String nomeArquivo);

    void armazenar(NovaFoto novaFoto);

    void remover(String nomeArquivo);

    void verificarCompatibilidadeMediaType(MediaType mediaType, List<MediaType> acceptMediaTypes) throws HttpMediaTypeNotAcceptableException;

    default String gerarNovoNomeArquivo(String nomeOriginal) {
        return UUID.randomUUID() + "_" + nomeOriginal;
    }

    default void substituir(String nomeArquivoExistente, NovaFoto novaFoto) {
        this.armazenar(novaFoto);

        if (nomeArquivoExistente != null) {
            this.remover(nomeArquivoExistente);
        }
    }



    @Builder
    @Getter
    class NovaFoto {
        private String nomeArquivo;
        private InputStream inputStream;
    }
}
