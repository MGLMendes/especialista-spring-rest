package com.algaworks.algafood.core.config;

import com.algaworks.algafood.api.v1.model.dto.EnderecoDTO;
import com.algaworks.algafood.api.v1.model.dto.RestauranteDTO;
import com.algaworks.algafood.api.v1.model.input.ItemPedidoInput;
import com.algaworks.algafood.api.v2.model.input.CidadeInputV2;
import com.algaworks.algafood.domain.model.Cidade;
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

        modelMapper.createTypeMap(CidadeInputV2.class, Cidade.class)
                .addMappings(mapper -> mapper.skip(Cidade::setId));

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
