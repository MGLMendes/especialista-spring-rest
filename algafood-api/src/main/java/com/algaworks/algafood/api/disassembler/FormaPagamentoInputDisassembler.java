package com.algaworks.algafood.api.disassembler;

import com.algaworks.algafood.api.model.input.FormaPagamentoInput;
import com.algaworks.algafood.domain.model.FormaPagamento;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FormaPagamentoInputDisassembler {

    private final ModelMapper modelMapper;

    public FormaPagamento toDomainObject(FormaPagamentoInput formaPagamentoIdInput) {
        return modelMapper.map(formaPagamentoIdInput, FormaPagamento.class);
    }

    public void copyToDomainObject(FormaPagamentoInput formaPagamentoIdInput, FormaPagamento formaPagamento) {
        modelMapper.map(formaPagamentoIdInput, formaPagamento);

    }
}
