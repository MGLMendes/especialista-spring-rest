package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.CozinhaController;
import com.algaworks.algafood.api.links.AlgaLinks;
import com.algaworks.algafood.api.model.dto.CozinhaDTO;
import com.algaworks.algafood.domain.model.Cozinha;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class CozinhaDTOAssembler extends
        RepresentationModelAssemblerSupport<Cozinha, CozinhaDTO> {

    private final ModelMapper modelMapper;

    private final AlgaLinks algaLinks;

    public CozinhaDTOAssembler(ModelMapper modelMapper, AlgaLinks algaLinks) {
        super(CozinhaController.class, CozinhaDTO.class);
        this.modelMapper = modelMapper;
        this.algaLinks = algaLinks;
    }

    @Override
    public CozinhaDTO toModel(Cozinha cozinha) {
        CozinhaDTO cozinhaModel = createModelWithId(cozinha.getId(), cozinha);
        modelMapper.map(cozinha, cozinhaModel);

        cozinhaModel.add(algaLinks.linkToCozinhas("cozinhas"));

        return cozinhaModel;
    }



}
