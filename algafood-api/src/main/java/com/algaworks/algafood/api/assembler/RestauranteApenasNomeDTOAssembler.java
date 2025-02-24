package com.algaworks.algafood.api.assembler;


import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.links.AlgaLinks;
import com.algaworks.algafood.api.model.dto.RestauranteApenasNomeDTO;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteApenasNomeDTOAssembler
        extends RepresentationModelAssemblerSupport<Restaurante, RestauranteApenasNomeDTO> {

    private final ModelMapper modelMapper;
    
    private final AlgaLinks algaLinks;
    
    public RestauranteApenasNomeDTOAssembler(ModelMapper modelMapper, AlgaLinks algaLinks) {
        super(RestauranteController.class, RestauranteApenasNomeDTO.class);
        this.modelMapper = modelMapper;
        this.algaLinks = algaLinks;
    }

    @Override
    public RestauranteApenasNomeDTO toModel(Restaurante restaurante) {
        RestauranteApenasNomeDTO restauranteModel = createModelWithId(
                restaurante.getId(), restaurante);
        
        modelMapper.map(restaurante, restauranteModel);
        
        restauranteModel.add(algaLinks.linkToRestaurantes("restaurantes"));
        
        return restauranteModel;
    }

    @Override
    public CollectionModel<RestauranteApenasNomeDTO> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities)
                .add(algaLinks.linkToRestaurantes());
    }   
}