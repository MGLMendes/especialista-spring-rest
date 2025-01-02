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
public class EnderecoInput {

    @NotBlank
    private String cep;
    @NotBlank
    private String logradouro;
    @NotBlank
    private String numero;
    private String complemento;
    @NotBlank
    private String bairro;
    @Valid
    @NotNull
    private CidadeIdInput cidade;
}
