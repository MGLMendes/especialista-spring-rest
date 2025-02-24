package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.EstadoController;
import com.algaworks.algafood.api.model.dto.EstadoDTO;
import com.algaworks.algafood.domain.model.Estado;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class EstadoDTOAssembler extends RepresentationModelAssemblerSupport<Estado, EstadoDTO> {

    private final ModelMapper modelMapper;

    public EstadoDTOAssembler(ModelMapper modelMapper) {
        super(EstadoController.class, EstadoDTO.class);
        this.modelMapper = modelMapper;
    }

    @Override
    public EstadoDTO toModel(Estado estado) {
        EstadoDTO EstadoDTO = createModelWithId(estado.getId(), estado);
        modelMapper.map(estado, EstadoDTO);

        EstadoDTO.add(linkTo(EstadoController.class).withRel("estados"));

        return EstadoDTO;
    }

    @Override
    public CollectionModel<EstadoDTO> toCollectionModel(Iterable<? extends Estado> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(EstadoController.class).withSelfRel());
    }

}
