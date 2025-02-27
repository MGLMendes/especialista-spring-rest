package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.links.AlgaLinks;
import com.algaworks.algafood.api.model.dto.PermissaoDTO;
import com.algaworks.algafood.domain.model.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PermissaoDTOAssembler implements RepresentationModelAssembler<Permissao, PermissaoDTO> {

    private final ModelMapper modelMapper;

    private final AlgaLinks algaLinks;

    public PermissaoDTOAssembler(ModelMapper modelMapper, AlgaLinks algaLinks) {
        this.modelMapper = modelMapper;
        this.algaLinks = algaLinks;
    }

    @Override
    public PermissaoDTO toModel(Permissao permissao) {
        return modelMapper.map(permissao, PermissaoDTO.class);
    }

    @Override
    public CollectionModel<PermissaoDTO> toCollectionModel(Iterable<? extends Permissao> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities)
                .add(algaLinks.linkToPermissoes());
    }


}
