package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.dto.FormaPagamentoDTO;
import com.algaworks.algafood.domain.model.FormaPagamento;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FormaPagamentoDTOAssembler {

    private final ModelMapper modelMapper;

    public FormaPagamentoDTO toModel(FormaPagamento formaPagamento) {
        return modelMapper.map(formaPagamento, FormaPagamentoDTO.class);
    }

    public List<FormaPagamentoDTO> toCollectionList(List<FormaPagamento> formaPagamentos) {
        return formaPagamentos.stream().map(
                this::toModel  // formaPagamentos -> toModel(formaPagamento)
        ).collect(Collectors.toList());
    }

}
