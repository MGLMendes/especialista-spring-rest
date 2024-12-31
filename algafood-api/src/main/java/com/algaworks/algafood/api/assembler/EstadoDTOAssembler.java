package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.dto.EstadoDTO;
import com.algaworks.algafood.domain.model.Estado;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EstadoDTOAssembler {

    private final ModelMapper modelMapper;

    public EstadoDTO toModel(Estado estado) {
        return modelMapper.map(estado, EstadoDTO.class);
    }

    public List<EstadoDTO> toCollectionList(List<Estado> estados) {
        return estados.stream().map(
                this::toModel  // estados -> toModel(estado)
        ).collect(Collectors.toList());
    }

}
