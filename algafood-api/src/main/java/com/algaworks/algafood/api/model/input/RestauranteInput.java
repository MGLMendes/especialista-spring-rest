package com.algaworks.algafood.api.model.input;

import com.algaworks.algafood.core.validation.annotations.Multiplo;
import com.algaworks.algafood.core.validation.annotations.TaxaFrete;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class RestauranteInput {

    @NotBlank
    private String nome;

    @NotNull
    @TaxaFrete
    @Multiplo(numero = 5)
    private BigDecimal taxaFrete;

    @Valid
    @NotNull
    private CozinhaIdInput cozinha;

    @Valid
    @NotNull
    private EnderecoInput endereco;
}
