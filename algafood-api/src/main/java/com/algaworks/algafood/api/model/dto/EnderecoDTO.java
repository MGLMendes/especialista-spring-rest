package com.algaworks.algafood.api.model.dto;

import com.algaworks.algafood.domain.model.Cidade;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Embeddable
public class EnderecoDTO {
    @ApiModelProperty(example = "38400-000")
    private String cep;

    @ApiModelProperty(example = "Rua Floriano Peixoto")
    private String logradouro;

    @ApiModelProperty(example = "1500")
    private String numero;

    @ApiModelProperty(example = "Apto 901")
    private String complemento;

    @ApiModelProperty(example = "Centro")
    private String bairro;
    private EnderecoCidadeDTO cidade;
}
