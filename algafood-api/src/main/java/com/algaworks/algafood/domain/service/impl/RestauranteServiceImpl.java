package com.algaworks.algafood.domain.service.impl;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CozinhaService;
import com.algaworks.algafood.domain.service.RestauranteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestauranteServiceImpl implements RestauranteService {

    public static final String MSG_COZINHA_NAO_ENCONTRADA = "Não existe cadastro de cozinha com o código %d";


    private static final String MSG_RESTAURANTE_NAO_ENCONTRADO
            = "Não existe um cadastro de restaurante com código %d";

    private final RestauranteRepository restauranteRepository;
    private final CozinhaService cozinhaService;

    @Override
    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();

        try {
            Cozinha cozinha = cozinhaService.buscar(cozinhaId);

            restaurante.setCozinha(cozinha);

            return restauranteRepository.save(restaurante);
        } catch (EntidadeNaoEncontradaException e ) {
            throw new NegocioException(
                    String.format(
                            MSG_COZINHA_NAO_ENCONTRADA,
                            restaurante.getCozinha().getId()
                    )
            );
        }
    }

    @Override
    public List<Restaurante> listar() {
        return restauranteRepository.findAll();
    }

    @Override
    public Restaurante buscar(Long restauranteId) {
        return restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format(MSG_RESTAURANTE_NAO_ENCONTRADO, restauranteId)));
    }

    @Override
    public Restaurante atualizar(Long restauranteId, Restaurante restaurante) {
        Restaurante restauranteSalvo = buscar(restauranteId);


        BeanUtils.copyProperties(
                restaurante, restauranteSalvo, "id",
                "formasPagamento",
                "endereco",
                "dataCadastro",
                "produtos"
        );

        try {
            return salvar(restauranteSalvo);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(
                    String.format(
                            MSG_COZINHA_NAO_ENCONTRADA,
                            restaurante.getCozinha().getId()
                    )
            );
        }
    }


}
