package com.algaworks.algafood.domain.service.impl;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.RestauranteService;
import com.algaworks.algafood.infra.repository.RestauranteRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestauranteServiceImpl implements RestauranteService {

    private final RestauranteRepository restauranteRepository;

    @Override
    public Restaurante salvar(Restaurante restaurante) {
        return restauranteRepository.salvar(restaurante);
    }

    @Override
    public List<Restaurante> listar() {
        return restauranteRepository.listar();
    }

    @Override
    public Restaurante buscar(Long restauranteId) {
        Restaurante restaurante = restauranteRepository.buscar(restauranteId);

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


}
