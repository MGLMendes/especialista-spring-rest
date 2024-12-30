package com.algaworks.algafood.core.validation.annotations.validators;

import com.algaworks.algafood.core.validation.annotations.Multiplo;
import lombok.Getter;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class MultiploValidator implements ConstraintValidator<Multiplo, Number> {

    private int numeroMultiplo;

    @Override
    public void initialize(Multiplo constraintAnnotation) {
        this.numeroMultiplo = constraintAnnotation.numero();
    }

    @Override
    public boolean isValid(Number number, ConstraintValidatorContext constraintValidatorContext) {
        if (number != null) {
            var valorDecimal = BigDecimal.valueOf(number.doubleValue());

            var multiploDecimal = BigDecimal.valueOf(this.numeroMultiplo);

            var resto = valorDecimal.remainder(multiploDecimal);

            return BigDecimal.ZERO.compareTo(resto) == 0;

        }
        return true;
    }
}
