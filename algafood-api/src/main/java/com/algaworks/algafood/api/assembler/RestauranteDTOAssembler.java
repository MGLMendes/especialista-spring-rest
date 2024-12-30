package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.dto.CozinhaDTO;
import com.algaworks.algafood.api.model.dto.RestauranteDTO;
import com.algaworks.algafood.domain.model.Restaurante;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RestauranteDTOAssembler {

    public RestauranteDTO toModel(Restaurante restaurante) {
        CozinhaDTO cozinhaDTO = new CozinhaDTO();
        cozinhaDTO.setId(restaurante.getCozinha().getId());
        cozinhaDTO.setNome(restaurante.getCozinha().getNome());

        RestauranteDTO restauranteDTO = new RestauranteDTO();
        restauranteDTO.setId(restaurante.getId());
        restauranteDTO.setNome(restaurante.getNome());
        restauranteDTO.setTaxaFrete(restaurante.getTaxaFrete());
        restauranteDTO.setCozinha(cozinhaDTO);
        return restauranteDTO;
    }

    public List<RestauranteDTO> toCollectionList(List<Restaurante> restaurantes) {
        return restaurantes.stream().map(
                this::toModel  // restaurante -> toModel(restaurante)
        ).collect(Collectors.toList());
    }

}
