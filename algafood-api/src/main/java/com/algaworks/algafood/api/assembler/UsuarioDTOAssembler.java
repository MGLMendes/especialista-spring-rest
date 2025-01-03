package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.dto.UsuarioDTO;
import com.algaworks.algafood.domain.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UsuarioDTOAssembler {


    private final ModelMapper modelMapper;

    public UsuarioDTO toModel(Usuario grupo) {
        return modelMapper.map(grupo, UsuarioDTO.class);
    }

    public List<UsuarioDTO> toCollectionList(List<Usuario> grupos) {
        return grupos.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

}
