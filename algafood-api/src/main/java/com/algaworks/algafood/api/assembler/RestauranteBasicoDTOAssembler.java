package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.links.AlgaLinks;
import com.algaworks.algafood.api.model.dto.RestauranteBasicoDTO;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteBasicoDTOAssembler
        extends RepresentationModelAssemblerSupport<Restaurante, RestauranteBasicoDTO> {

    private final ModelMapper modelMapper;
    
    private final AlgaLinks algaLinks;
    
    public RestauranteBasicoDTOAssembler(ModelMapper modelMapper, AlgaLinks algaLinks) {
        super(RestauranteController.class, RestauranteBasicoDTO.class);
        this.modelMapper = modelMapper;
        this.algaLinks = algaLinks;
    }
    
    @Override
    public RestauranteBasicoDTO toModel(Restaurante restaurante) {
        RestauranteBasicoDTO restauranteModel = createModelWithId(
                restaurante.getId(), restaurante);
        
        modelMapper.map(restaurante, restauranteModel);
        
        restauranteModel.add(algaLinks.linkToRestaurantes("restaurantes"));
        
        restauranteModel.getCozinha().add(
                algaLinks.linkToCozinha(restaurante.getCozinha().getId()));
        
        return restauranteModel;
    }
    
    @Override
    public CollectionModel<RestauranteBasicoDTO> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities)
                .add(algaLinks.linkToRestaurantes());
    }   
}        