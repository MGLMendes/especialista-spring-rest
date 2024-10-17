package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CozinhaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/teste")
@RequiredArgsConstructor
public class TestController {

    private final CozinhaRepository cozinhaRepository;

    private final RestauranteRepository restauranteRepository;

    @GetMapping("/cozinhas-por-nome")
    public List<Cozinha> listar(@RequestParam String nome) {
        return cozinhaRepository.findByNomeContaining(nome);
    }


    @GetMapping("/restaurantes-por-taxa-frete")
    public List<Restaurante> restaurantesPorTaxaFrete(
            @RequestParam BigDecimal taxaInicial,
            @RequestParam BigDecimal taxaFinal
            ) {
        return restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
    }

    @GetMapping("/restaurantes-e-cozinha-por-id")
    public List<Restaurante> restaurantesECozinhasPorId(
            @RequestParam String nome,
            @RequestParam Long cozinhaId
    ) {
        return restauranteRepository.findByNomeContainingAndCozinhaId(nome, cozinhaId);
    }


}
