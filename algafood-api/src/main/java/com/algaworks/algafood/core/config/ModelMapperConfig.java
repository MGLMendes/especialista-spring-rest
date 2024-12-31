package com.algaworks.algafood.core.config;

import com.algaworks.algafood.api.model.dto.RestauranteDTO;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.createTypeMap(
                Restaurante.class, RestauranteDTO.class
        ).addMapping(
                Restaurante::getTaxaFrete, RestauranteDTO::setPrecoFrete
        );

        return modelMapper;
    }
}
