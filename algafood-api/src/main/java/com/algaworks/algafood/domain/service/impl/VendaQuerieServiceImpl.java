package com.algaworks.algafood.domain.service.impl;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiariaDTO;
import com.algaworks.algafood.domain.service.VendaQuerieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VendaQuerieServiceImpl implements VendaQuerieService {

    private final EntityManager entityManager;


    @Override
    public List<VendaDiariaDTO> consultarVendasDiarias(VendaDiariaFilter filtro) {
        return List.of();
    }
}
