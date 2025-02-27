package com.algaworks.algafood.api.v1.disassembler;

import com.algaworks.algafood.api.v1.model.input.CozinhaInput;
import com.algaworks.algafood.domain.model.Cozinha;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CozinhaInputDisassembler {

    private final ModelMapper modelMapper;

    public Cozinha toDomainObject(CozinhaInput cozinhaIdInput) {
        return modelMapper.map(cozinhaIdInput, Cozinha.class);
    }

    public void copyToDomainObject(CozinhaInput cozinhaIdInput, Cozinha cozinha) {
        modelMapper.map(cozinhaIdInput, cozinha);

    }
}
