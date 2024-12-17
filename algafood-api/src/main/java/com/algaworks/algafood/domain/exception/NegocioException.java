package com.algaworks.algafood.domain.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NegocioException extends RuntimeException{

    private String message;

    public NegocioException(String message) {
        this.message = message;
    }
}
