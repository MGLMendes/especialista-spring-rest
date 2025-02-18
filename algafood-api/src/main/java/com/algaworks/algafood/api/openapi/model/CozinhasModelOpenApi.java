package com.algaworks.algafood.api.openapi.model;

import com.algaworks.algafood.api.model.dto.CozinhaDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@ApiModel("CozinhasModel")
@Getter
@Setter
public class CozinhasModelOpenApi extends GenericPageModelOpenApi<CozinhaDTO> {

}
