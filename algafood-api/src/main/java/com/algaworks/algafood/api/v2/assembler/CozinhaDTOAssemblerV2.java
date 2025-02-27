package com.algaworks.algafood.api.v2.assembler;

import com.algaworks.algafood.api.v1.controller.CozinhaController;
import com.algaworks.algafood.api.v1.links.AlgaLinks;
import com.algaworks.algafood.api.v1.model.dto.CozinhaDTO;
import com.algaworks.algafood.api.v2.links.AlgaLinksV2;
import com.algaworks.algafood.api.v2.model.dto.CozinhaDTOV2;
import com.algaworks.algafood.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CozinhaDTOAssemblerV2 extends
        RepresentationModelAssemblerSupport<Cozinha, CozinhaDTOV2> {

    private final ModelMapper modelMapper;

    private final AlgaLinksV2 algaLinks;

    public CozinhaDTOAssemblerV2(ModelMapper modelMapper, AlgaLinksV2 algaLinks) {
        super(CozinhaController.class, CozinhaDTOV2.class);
        this.modelMapper = modelMapper;
        this.algaLinks = algaLinks;
    }

    @Override
    public CozinhaDTOV2 toModel(Cozinha cozinha) {
        CozinhaDTOV2 cozinhaModel = createModelWithId(cozinha.getId(), cozinha);
        modelMapper.map(cozinha, cozinhaModel);

        cozinhaModel.add(algaLinks.linkToCozinhas("cozinhas"));

        return cozinhaModel;
    }



}
