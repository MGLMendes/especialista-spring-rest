package com.algaworks.algafood.api.v1.model.input;

import com.algaworks.algafood.core.validation.annotations.Multiplo;
import com.algaworks.algafood.core.validation.annotations.TaxaFrete;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class EnderecoInput {

    @ApiModelProperty(example = "38400-000", required = true)
    @NotBlank
    private String cep;

    @ApiModelProperty(example = "Rua Floriano Peixoto", required = true)
    @NotBlank
    private String logradouro;

    @ApiModelProperty(example = "\"1500\"", required = true)
    @NotBlank
    private String numero;;

    @ApiModelProperty(example = "Apto 901")
    private String complemento;

    @ApiModelProperty(example = "Centro", required = true)
    @NotBlank
    private String bairro;

    @Valid
    @NotNull
    private CidadeIdInput cidade;

}
