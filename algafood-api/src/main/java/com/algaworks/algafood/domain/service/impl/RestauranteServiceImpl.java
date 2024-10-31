package com.algaworks.algafood.domain.service.impl;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.RestauranteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestauranteServiceImpl implements RestauranteService {

    private final RestauranteRepository restauranteRepository;
    private final CozinhaRepository cozinhaRepository;

    @Override
    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        Optional<Cozinha> cozinha = cozinhaRepository.findById(cozinhaId);

        if (cozinha.isEmpty()) {
            throw new EntidadeNaoEncontradaException(String.format("Não existe cadastro de cozinha com o código %d", cozinhaId));
        }

        restaurante.setCozinha(cozinha.get());

        return restauranteRepository.save(restaurante);
    }

    @Override
    public List<Restaurante> listar() {
        return restauranteRepository.findAll();
    }

    @Override
    public Restaurante buscar(Long restauranteId) {
        Restaurante restaurante = restauranteRepository.findById(restauranteId).orElse(null);

        if (restaurante == null) {
            throw new EntidadeNaoEncontradaException(
                    String.format(
                            "Não existe entidade restaurante com código %d",
                            restauranteId
                    )
            );
        }

        return restaurante;
    }

    @Override
    public Restaurante atualizar(Long restauranteId, Restaurante restaurante) {
        Restaurante restauranteSalvo = buscar(restauranteId);


        BeanUtils.copyProperties(
                restaurante, restauranteSalvo, "id", "formasPagamento", "endereco"
        );


        return salvar(restauranteSalvo);
    }


}
