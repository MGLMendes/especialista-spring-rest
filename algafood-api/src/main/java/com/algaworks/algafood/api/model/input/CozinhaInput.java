package com.algaworks.algafood.api.model.input;

import com.algaworks.algafood.core.validation.groups.ValidationGroups;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CozinhaInput {
    @NotNull
    private Long id;
}
