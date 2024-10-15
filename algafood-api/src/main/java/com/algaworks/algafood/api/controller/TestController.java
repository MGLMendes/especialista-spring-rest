package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CozinhaService;
import lombok.RequiredArgsConstructor;
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
        return cozinhaRepository.findByNome(nome);
    }


}
