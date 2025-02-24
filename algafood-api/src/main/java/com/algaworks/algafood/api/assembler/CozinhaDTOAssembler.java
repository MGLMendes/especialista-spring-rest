package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.CozinhaController;
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

    public CozinhaDTOAssembler(ModelMapper modelMapper) {
        super(CozinhaController.class, CozinhaDTO.class);
        this.modelMapper = modelMapper;
    }

    @Override
    public CozinhaDTO toModel(Cozinha cozinha) {
        CozinhaDTO cozinhaDTO = createModelWithId(cozinha.getId(), cozinha);

        modelMapper.map(cozinha, cozinhaDTO);

        cozinhaDTO.add(linkTo(CozinhaController.class).withRel("cozinhas"));

        return cozinhaDTO;
    }



}
