package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.algaworks.algafood.infra.factory.RestaurantesSpecs.comFreteGratis;
import static com.algaworks.algafood.infra.factory.RestaurantesSpecs.comNomeSemelhante;

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

    @GetMapping("/exist-cozinhas-por-nome")
    public boolean existPorNome(@RequestParam String nome) {
        return cozinhaRepository.existsByNome(nome);
    }


    @GetMapping("/restaurantes-por-taxa-frete")
    public List<Restaurante> restaurantesPorTaxaFrete(
            @RequestParam BigDecimal taxaInicial,
            @RequestParam BigDecimal taxaFinal
            ) {
        return restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
    }

    @GetMapping("/restaurantes-por-nome-taxa-frete")
    public List<Restaurante> restaurantesPorNomeTaxaFrete(
            String nome,
            BigDecimal taxaInicial,
            BigDecimal taxaFinal
    ) {
        return restauranteRepository.find(nome, taxaInicial, taxaFinal);
    }

    @GetMapping("/restaurantes-e-cozinha-por-id")
    public List<Restaurante> restaurantesECozinhasPorId(
            @RequestParam String nome,
            @RequestParam Long cozinhaId
    ) {
        return restauranteRepository.consultarPorNome(nome, cozinhaId);
    }

    @GetMapping("/restaurantes-count-cozinha-por-id")
    public int restaurantesCountCozinhasPorId(
            @RequestParam Long cozinhaId
    ) {
        return restauranteRepository.countByCozinhaId(cozinhaId);
    }

    @GetMapping("/restaurantes-primeiro-por-nome")
    public Optional<Restaurante> restaurantesPrimeiroPorNome(
            @RequestParam String nome
    ) {
        return restauranteRepository.findFirstByNomeContaining(nome);
    }

    @GetMapping("/restaurantes-top-por-nome")
    public List<Restaurante> restaurantesTopPorNome(
            @RequestParam String nome
    ) {
        return restauranteRepository.findTop2ByNomeContaining(nome);
    }

    @GetMapping("/restaurantes-com-frete-gratis-e-nome-igual")
    public List<Restaurante> restaurantesComFreteGratis(
            @RequestParam String nome
    ) {


        return restauranteRepository.findAll(
                comFreteGratis().and(comNomeSemelhante(nome)));
    }

    @GetMapping("/restaurantes-primeiro")
    public Restaurante buscarPrimeiroRestaurante(
    ) {
        return restauranteRepository.buscarPrimeiro().orElse(null);
    }

    @GetMapping("/cozinhas-primeiro")
    public Cozinha buscarPrimeiraCozinha(
    ) {
        return cozinhaRepository.buscarPrimeiro().orElse(null);
    }


}
