package com.algaworks.algafood.api.model.dto;

import com.algaworks.algafood.domain.model.Cidade;
import lombok.Data;

import javax.persistence.*;

@Data
@Embeddable
public class EnderecoDTO {
    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private EnderecoCidadeDTO cidade;
}
