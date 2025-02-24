package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.UsuarioController;
import com.algaworks.algafood.api.controller.UsuarioGruposController;
import com.algaworks.algafood.api.model.dto.UsuarioDTO;
import com.algaworks.algafood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UsuarioDTOAssembler extends RepresentationModelAssemblerSupport<Usuario, UsuarioDTO> {


    private final ModelMapper modelMapper;

    public UsuarioDTOAssembler(ModelMapper modelMapper) {
        super(UsuarioController.class, UsuarioDTO.class);
        this.modelMapper = modelMapper;
    }

    @Override
    public UsuarioDTO toModel(Usuario usuario) {
        UsuarioDTO UsuarioDTO = createModelWithId(usuario.getId(), usuario);
        modelMapper.map(usuario, UsuarioDTO);

        UsuarioDTO.add(linkTo(UsuarioController.class).withRel("usuarios"));

        UsuarioDTO.add(linkTo(methodOn(UsuarioGruposController.class)
                .listar(usuario.getId())).withRel("grupos-usuario"));

        return UsuarioDTO;
    }

    @Override
    public CollectionModel<UsuarioDTO> toCollectionModel(Iterable<? extends Usuario> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(UsuarioController.class).withSelfRel());
    }

}
