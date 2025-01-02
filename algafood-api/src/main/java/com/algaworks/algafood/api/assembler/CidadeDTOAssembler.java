package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.dto.CidadeDTO;
import com.algaworks.algafood.domain.model.Cidade;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CidadeDTOAssembler {

    private final ModelMapper modelMapper;

    public CidadeDTO toModel(Cidade cidade) {
        return modelMapper.map(cidade, CidadeDTO.class);
    }

    public List<CidadeDTO> toCollectionList(List<Cidade> cidades) {
        return cidades.stream().map(
                this::toModel  // cidades -> toModel(cidade)
        ).collect(Collectors.toList());
    }

}
