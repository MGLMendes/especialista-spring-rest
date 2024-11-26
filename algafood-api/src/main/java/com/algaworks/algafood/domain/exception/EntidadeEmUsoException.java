package com.algaworks.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EntidadeEmUsoException extends ResponseStatusException {

    public EntidadeEmUsoException(HttpStatus status, String reason) {
        super(status, reason);
    }

    public EntidadeEmUsoException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
