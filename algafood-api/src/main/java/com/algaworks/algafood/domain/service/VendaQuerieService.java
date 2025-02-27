package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiariaDTO;

import java.util.List;

public interface VendaQuerieService {

    List<VendaDiariaDTO> consultarVendasDiarias(VendaDiariaFilter filtro);

}
