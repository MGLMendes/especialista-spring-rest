package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.controller.RestauranteController;
import com.algaworks.algafood.api.v1.links.AlgaLinks;
import com.algaworks.algafood.api.v1.model.dto.RestauranteBasicoDTO;
import com.algaworks.algafood.core.security.AlgaSecurity;
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

    private final AlgaSecurity algaSecurity;
    
    public RestauranteBasicoDTOAssembler(ModelMapper modelMapper, AlgaLinks algaLinks, AlgaSecurity algaSecurity) {
        super(RestauranteController.class, RestauranteBasicoDTO.class);
        this.modelMapper = modelMapper;
        this.algaLinks = algaLinks;
        this.algaSecurity = algaSecurity;
    }
    
    @Override
    public RestauranteBasicoDTO toModel(Restaurante restaurante) {
        RestauranteBasicoDTO restauranteModel = createModelWithId(
                restaurante.getId(), restaurante);
        
        modelMapper.map(restaurante, restauranteModel);

        if (algaSecurity.podeConsultarRestaurantes()) {
            restauranteModel.add(algaLinks.linkToRestaurantes("restaurantes"));
        }

        if (algaSecurity.podeConsultarCozinhas()) {
            restauranteModel.getCozinha().add(
                    algaLinks.linkToCozinha(restaurante.getCozinha().getId()));
        }
        
        return restauranteModel;
    }
    
    @Override
    public CollectionModel<RestauranteBasicoDTO> toCollectionModel(Iterable<? extends Restaurante> entities) {
        CollectionModel<RestauranteBasicoDTO> collectionModel = super.toCollectionModel(entities);

        if (algaSecurity.podeConsultarRestaurantes()) {
            collectionModel.add(algaLinks.linkToRestaurantes());
        }

        return collectionModel;
    }   
}        