package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.dto.FotoProdutoDTO;
import com.algaworks.algafood.domain.model.FotoProduto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FotoProdutoAssembler {

    private final ModelMapper mapper;


    public FotoProdutoDTO toDTO(FotoProduto fotoProduto) {
        return mapper.map(fotoProduto, FotoProdutoDTO.class);
    }

}
