package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.CidadeController;
import com.algaworks.algafood.api.controller.EstadoController;
import com.algaworks.algafood.api.links.AlgaLinks;
import com.algaworks.algafood.api.model.dto.CidadeDTO;
import com.algaworks.algafood.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CidadeDTOAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeDTO> {

    private final ModelMapper modelMapper;

    private final AlgaLinks algaLinks;

    public CidadeDTOAssembler(ModelMapper modelMapper, AlgaLinks algaLinks) {
        super(CidadeController.class, CidadeDTO.class);
        this.modelMapper = modelMapper;
        this.algaLinks = algaLinks;
    }

    @Override
    public CidadeDTO toModel(Cidade cidade) {
        CidadeDTO cidadeModel = createModelWithId(cidade.getId(), cidade);

        modelMapper.map(cidade, cidadeModel);

        cidadeModel.add(algaLinks.linkToCidades("cidades"));

        cidadeModel.getEstado().add(algaLinks.linkToEstado(cidadeModel.getEstado().getId()));

        return cidadeModel;
    }

    @Override
    public CollectionModel<CidadeDTO> toCollectionModel(Iterable<? extends Cidade> entities) {
        return super.toCollectionModel(entities).add(algaLinks.linkToCidades());
    }

}
