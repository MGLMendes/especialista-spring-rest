package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.controller.GrupoController;
import com.algaworks.algafood.api.v1.links.AlgaLinks;
import com.algaworks.algafood.api.v1.model.dto.GrupoDTO;
import com.algaworks.algafood.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class GrupoDTOAssembler extends RepresentationModelAssemblerSupport<Grupo, GrupoDTO> {


    private final AlgaLinks algaLinks;

    private final ModelMapper modelMapper;

    public GrupoDTOAssembler(AlgaLinks algaLinks, ModelMapper modelMapper) {
        super(GrupoController.class, GrupoDTO.class);
        this.algaLinks = algaLinks;
        this.modelMapper = modelMapper;
    }

    @Override
    public GrupoDTO toModel(Grupo grupo) {
        GrupoDTO grupoDTO = createModelWithId(grupo.getId(), grupo);
        modelMapper.map(grupo, grupoDTO);

        grupoDTO.add(algaLinks.linkToGrupos("grupos"));

        grupoDTO.add(algaLinks.linkToGrupoPermissoes(grupo.getId(), "permissoes"));

        return grupoDTO;
    }

    @Override
    public CollectionModel<GrupoDTO> toCollectionModel(Iterable<? extends Grupo> grupos) {
        return super.toCollectionModel(grupos)
                .add(algaLinks.linkToGrupos());
    }

}
