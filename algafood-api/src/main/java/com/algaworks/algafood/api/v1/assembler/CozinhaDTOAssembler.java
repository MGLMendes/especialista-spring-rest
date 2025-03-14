package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.controller.CozinhaController;
import com.algaworks.algafood.api.v1.links.AlgaLinks;
import com.algaworks.algafood.api.v1.model.dto.CozinhaDTO;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class CozinhaDTOAssembler extends
        RepresentationModelAssemblerSupport<Cozinha, CozinhaDTO> {

    private final ModelMapper modelMapper;

    private final AlgaLinks algaLinks;

    private final AlgaSecurity algaSecurity;

    public CozinhaDTOAssembler(ModelMapper modelMapper, AlgaLinks algaLinks, AlgaSecurity algaSecurity) {
        super(CozinhaController.class, CozinhaDTO.class);
        this.modelMapper = modelMapper;
        this.algaLinks = algaLinks;
        this.algaSecurity = algaSecurity;
    }

    @Override
    public CozinhaDTO toModel(Cozinha cozinha) {
        CozinhaDTO cozinhaModel = createModelWithId(cozinha.getId(), cozinha);
        modelMapper.map(cozinha, cozinhaModel);

        if (algaSecurity.podeConsultarCozinhas()) {
            cozinhaModel.add(algaLinks.linkToCozinhas("cozinhas"));
        }

        return cozinhaModel;
    }



}
