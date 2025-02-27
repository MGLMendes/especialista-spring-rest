package com.algaworks.algafood.api.v2.assembler;

import com.algaworks.algafood.api.v1.model.dto.CidadeDTO;
import com.algaworks.algafood.api.v2.controller.CidadeControllerV2;
import com.algaworks.algafood.api.v2.links.AlgaLinksV2;
import com.algaworks.algafood.api.v2.model.dto.CidadeDTOV2;
import com.algaworks.algafood.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CidadeDTOAssemblerV2 extends RepresentationModelAssemblerSupport<Cidade, CidadeDTOV2> {

    private final ModelMapper modelMapper;

    private final AlgaLinksV2 algaLinks;

    public CidadeDTOAssemblerV2(ModelMapper modelMapper, AlgaLinksV2 algaLinks) {
        super(CidadeControllerV2.class, CidadeDTOV2.class);
        this.modelMapper = modelMapper;
        this.algaLinks = algaLinks;
    }

    @Override
    public CidadeDTOV2 toModel(Cidade cidade) {
        CidadeDTOV2 cidadeModel = createModelWithId(cidade.getId(), cidade);

        modelMapper.map(cidade, cidadeModel);

        cidadeModel.add(algaLinks.linkToCidades("cidades"));

        return cidadeModel;
    }

    @Override
    public CollectionModel<CidadeDTOV2> toCollectionModel(Iterable<? extends Cidade> entities) {
        return super.toCollectionModel(entities).add(algaLinks.linkToCidades("cidades"));
    }

}
