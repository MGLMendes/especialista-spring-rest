package com.algaworks.algafood.core.config;

import com.algaworks.algafood.api.model.dto.EnderecoDTO;
import com.algaworks.algafood.api.model.dto.RestauranteDTO;
import com.algaworks.algafood.api.model.input.ItemPedidoInput;
import com.algaworks.algafood.domain.model.Endereco;
import com.algaworks.algafood.domain.model.ItemPedido;
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
                Endereco.class, EnderecoDTO.class
        ).<String>addMapping(
                source -> source.getCidade().getEstado().getNome(),
                (target, value) -> target.getCidade().setEstado(value));

        modelMapper.createTypeMap(
                Restaurante.class, RestauranteDTO.class
        ).addMapping(
                Restaurante::getTaxaFrete, RestauranteDTO::setPrecoFrete
        );

        modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
                .addMappings(mapper -> mapper.skip(ItemPedido::setId));
        return modelMapper;
    }
}
