package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.links.AlgaLinks;
import com.algaworks.algafood.api.model.dto.CozinhaDTO;
import com.algaworks.algafood.api.model.dto.RestauranteDTO;
import com.algaworks.algafood.domain.model.Restaurante;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestauranteDTOAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteDTO> {

    private final ModelMapper modelMapper;

    private final AlgaLinks algaLinks;

    public RestauranteDTOAssembler(ModelMapper modelMapper, AlgaLinks algaLinks) {
        super(RestauranteController.class, RestauranteDTO.class);
        this.modelMapper = modelMapper;
        this.algaLinks = algaLinks;
    }

    @Override
    public RestauranteDTO toModel(Restaurante restaurante) {
        RestauranteDTO restauranteDTO = createModelWithId(restaurante.getId(), restaurante);
        modelMapper.map(restaurante, restauranteDTO);

        restauranteDTO.add(algaLinks.linkToRestaurantes("restaurantes"));

        restauranteDTO.getCozinha().add(
                algaLinks.linkToCozinha(restaurante.getCozinha().getId()));

        if (restaurante.getEndereco() != null) {
            restauranteDTO.getEndereco().getCidade().add(
                    algaLinks.linkToCidade(restaurante.getEndereco().getCidade().getId()));
        }

        restauranteDTO.add(algaLinks.linkToRestauranteFormasPagamento(restaurante.getId(),
                "formas-pagamento"));

        restauranteDTO.add(algaLinks.linkToResponsaveisRestaurante(restaurante.getId(),
                "responsaveis"));

        return restauranteDTO;
    }

    @Override
    public CollectionModel<RestauranteDTO> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities)
                .add(algaLinks.linkToRestaurantes());
    }

}
