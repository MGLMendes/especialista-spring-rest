package com.algaworks.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

public class EntidadeNaoEncontradaException extends ResponseStatusException {

    public EntidadeNaoEncontradaException(HttpStatus status, String reason) {
        super(status, reason);
    }

    public EntidadeNaoEncontradaException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
