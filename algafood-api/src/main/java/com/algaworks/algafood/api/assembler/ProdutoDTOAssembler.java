package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.dto.ProdutoDTO;
import com.algaworks.algafood.domain.model.Produto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProdutoDTOAssembler {

    private final ModelMapper modelMapper;

    public ProdutoDTO toModel(Produto produto) {
        return modelMapper.map(produto, ProdutoDTO.class);
    }

    public List<ProdutoDTO> toCollectionList(Collection<Produto> produtos) {
        return produtos.stream().map(
                this::toModel  // produtos -> toModel(produto)
        ).collect(Collectors.toList());
    }

}
