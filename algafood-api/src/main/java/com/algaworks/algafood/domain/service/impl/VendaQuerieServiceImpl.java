package com.algaworks.algafood.domain.service.impl;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.dto.VendaDiariaDTO;
import com.algaworks.algafood.domain.model.enums.StatusPedido;
import com.algaworks.algafood.domain.service.VendaQuerieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository // Para o spring tratar exception de persistencias
@RequiredArgsConstructor
public class VendaQuerieServiceImpl implements VendaQuerieService {

    private final EntityManager entityManager;


    @Override
    public List<VendaDiariaDTO> consultarVendasDiarias(VendaDiariaFilter filtro) {
        var builder = entityManager.getCriteriaBuilder();

        var query = builder.createQuery(VendaDiariaDTO.class);

        var root = query.from(Pedido.class);

        var functionDateDataCriacao = builder.function(
                "date", Date.class, root.get("dataCriacao")
        );

        var selection = builder.construct(
                VendaDiariaDTO.class,
                functionDateDataCriacao,
                builder.count(root.get("id")),
                builder.sum(root.get("valorTotal"))
        );

        query.select(selection);

        query.groupBy(functionDateDataCriacao);

        var predicates = new ArrayList<Predicate>();

        if (filtro.getRestauranteId() != null) {
            predicates.add(builder.equal(
                    root.get("restaurante"), filtro.getRestauranteId()
            ));
        }

        if (filtro.getDataCriacaoInicio() != null) {
            predicates.add(builder.greaterThanOrEqualTo(
                    root.get("dataCriacao"), filtro.getDataCriacaoInicio()
            ));
        }

        if (filtro.getDataCriacaoFim() != null) {
            predicates.add(builder.lessThanOrEqualTo(
                    root.get("dataCriacao"), filtro.getDataCriacaoInicio()
            ));
        }

        predicates.add(root.get("status").in(
                StatusPedido.CONFIRMADO, StatusPedido.ENTREGUE));

        query.where(
                predicates.toArray(new Predicate[0])
        );


        return entityManager.createQuery(query).getResultList();
    }
}
