package com.algaworks.algafood.infra.factory;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.filter.PedidoFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;

public class PedidoSpecs {

    public static Specification<Pedido> usandoFiltro(PedidoFilter filtro) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            root.fetch("restaurante");
            var predicates = new ArrayList<Predicate>();

            if (filtro.getClienteId() != null) {
                predicates.add(criteriaBuilder.equal(
                        root.get("cliente"), filtro.getClienteId()
                ));
            }

            if (filtro.getRestauranteId() != null) {
                predicates.add(criteriaBuilder.equal(
                        root.get("restaurante"), filtro.getRestauranteId()
                ));
            }

            if (filtro.getDataCriacaoInicio() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("dataCriacao"), filtro.getDataCriacaoInicio()
                ));
            }

            if (filtro.getDataCriacaoFim() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("dataCriacao"), filtro.getDataCriacaoInicio()
                ));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        };
    }

}
