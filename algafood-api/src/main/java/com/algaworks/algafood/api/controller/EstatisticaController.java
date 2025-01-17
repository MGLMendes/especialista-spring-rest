package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiariaDTO;
import com.algaworks.algafood.domain.service.VendaQuerieService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/estatisticas")
@RequiredArgsConstructor
public class EstatisticaController {

    private final VendaQuerieService vendaQuerieService;

    @GetMapping("/vendas-diarias")
    public ResponseEntity<List<VendaDiariaDTO>> consultarVendasDiarias(VendaDiariaFilter vendaDiariaFilter) {
        return ResponseEntity.ok(vendaQuerieService.consultarVendasDiarias(vendaDiariaFilter));
    }
}
