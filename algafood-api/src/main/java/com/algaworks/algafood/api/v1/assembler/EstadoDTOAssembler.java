package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.controller.EstadoController;
import com.algaworks.algafood.api.v1.links.AlgaLinks;
import com.algaworks.algafood.api.v1.model.dto.EstadoDTO;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class EstadoDTOAssembler extends RepresentationModelAssemblerSupport<Estado, EstadoDTO> {

    private final ModelMapper modelMapper;

    private final AlgaLinks algaLinks;

    private final AlgaSecurity algaSecurity;

    public EstadoDTOAssembler(ModelMapper modelMapper, AlgaLinks algaLinks, AlgaSecurity algaSecurity) {
        super(EstadoController.class, EstadoDTO.class);
        this.modelMapper = modelMapper;
        this.algaLinks = algaLinks;
        this.algaSecurity = algaSecurity;
    }

    @Override
    public EstadoDTO toModel(Estado estado) {
        EstadoDTO estadoModel = createModelWithId(estado.getId(), estado);
        modelMapper.map(estado, estadoModel);

        if (algaSecurity.podeConsultarEstados()) {
            estadoModel.add(algaLinks.linkToEstados("estados"));
        }

        return estadoModel;
    }

    @Override
    public CollectionModel<EstadoDTO> toCollectionModel(Iterable<? extends Estado> entities) {
        CollectionModel<EstadoDTO> collectionModel = super.toCollectionModel(entities);

        if (algaSecurity.podeConsultarEstados()) {
            collectionModel.add(algaLinks.linkToEstados());
        }

        return collectionModel;
    }

}
