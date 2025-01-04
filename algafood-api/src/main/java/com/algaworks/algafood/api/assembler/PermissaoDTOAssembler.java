package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.dto.PermissaoDTO;
import com.algaworks.algafood.domain.model.Permissao;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PermissaoDTOAssembler {

    private final ModelMapper modelMapper;

    public PermissaoDTO toModel(Permissao cidade) {
        return modelMapper.map(cidade, PermissaoDTO.class);
    }

    public List<PermissaoDTO> toCollectionList(Collection<Permissao> cidades) {
        return cidades.stream().map(
                this::toModel  // cidades -> toModel(cidade)
        ).collect(Collectors.toList());
    }

}
