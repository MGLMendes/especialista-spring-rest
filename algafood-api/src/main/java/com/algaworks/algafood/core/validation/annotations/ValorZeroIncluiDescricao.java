package com.algaworks.algafood.core.validation.annotations;

import com.algaworks.algafood.core.validation.annotations.validators.MultiploValidator;
import com.algaworks.algafood.core.validation.annotations.validators.ValorZeroIncluiDescricaoValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(
        validatedBy = {
                ValorZeroIncluiDescricaoValidator.class
        }
)
public @interface ValorZeroIncluiDescricao {

    String message() default "descrição obrigatória inválida!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String valorField();

    String descricaoField();

    String descricaoObrigatoria();

}
