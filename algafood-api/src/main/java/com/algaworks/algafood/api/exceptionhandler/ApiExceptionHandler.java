package com.algaworks.algafood.api.exceptionhandler;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException e,
                                             WebRequest webRequest) {

        Problem problem = createProblemBuilder(
                HttpStatus.NOT_FOUND,
                ProblemType.ENTIDADE_NAO_ENCONTRADA,
                e.getMessage()
        ).build();
        
        return handleExceptionInternal(
                e,
                problem,
                new HttpHeaders(),
                HttpStatus.NOT_FOUND,
                webRequest
        );
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handleNegocioException(NegocioException e, WebRequest webRequest) {
        return handleExceptionInternal(
                e,
                e.getMessage(),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                webRequest
        );
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException e,
                                                          WebRequest webRequest) {
        return handleExceptionInternal(
                e,
                e.getMessage(),
                new HttpHeaders(),
                HttpStatus.CONFLICT,
                webRequest
        );
    }


    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (body == null) {
            body = Problem.builder()
                    .status(status.value())
                    .title(status.getReasonPhrase()).build();
        } else if (body instanceof String) {
            body = Problem.builder()
                    .status(status.value())
                    .title((String) body).build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private Problem.ProblemBuilder createProblemBuilder(
            HttpStatus status,
            ProblemType problemType,
            String detail
    ){
        return Problem.builder()
                .status(status.value())
                .type(problemType.getUri())
                .title(problemType.getTitle())
                .detail(detail);
    }
}
