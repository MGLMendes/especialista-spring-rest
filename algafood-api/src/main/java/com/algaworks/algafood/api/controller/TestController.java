package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CozinhaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teste")
@RequiredArgsConstructor
public class TestController {

    private final CozinhaRepository cozinhaRepository;

    private final CozinhaService cozinhaService;

    @GetMapping("/cozinhas-por-nome")
    public List<Cozinha> listar(@RequestParam String nome) {
        return cozinhaRepository.consultarPorNome(nome);
    }


}
