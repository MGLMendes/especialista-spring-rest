package com.algaworks.algafood.api.v1.controller;


import com.algaworks.algafood.api.v1.assembler.FormaPagamentoDTOAssembler;
import com.algaworks.algafood.api.v1.links.AlgaLinks;
import com.algaworks.algafood.api.v1.model.dto.FormaPagamentoDTO;
import com.algaworks.algafood.api.v1.openapi.controller.RestauranteFormaPagamentoControllerOpenApi;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.core.security.annotations.CheckSecurity;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/restaurantes/{restauranteId}/formas-pagamento")
@RequiredArgsConstructor
public class RestauranteFormaPagamentoController implements RestauranteFormaPagamentoControllerOpenApi {

    private final RestauranteService restauranteService;

    private final FormaPagamentoDTOAssembler formaPagamentoDTOAssembler;

    private final AlgaLinks algaLinks;

    private final AlgaSecurity algaSecurity;

    @CheckSecurity.Restaurantes.PodeConsultar
    @Override
    @GetMapping
    public ResponseEntity<CollectionModel<FormaPagamentoDTO>> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.buscar(restauranteId);

        CollectionModel<FormaPagamentoDTO> formasPagamentoModel
                = formaPagamentoDTOAssembler.toCollectionModel(restaurante.getFormasPagamento())
                .removeLinks();

        formasPagamentoModel.add(algaLinks.linkToRestauranteFormasPagamento(restauranteId));

        if (algaSecurity.podeGerenciarFuncionamentoRestaurantes(restauranteId)) {
            formasPagamentoModel.add(algaLinks.linkToRestauranteFormasPagamentoAssociacao(restauranteId, "associar"));

            formasPagamentoModel.getContent().forEach(formaPagamentoModel -> {
                formaPagamentoModel.add(algaLinks.lintToRestauranteFormasPagamentoDesassociacao(
                        restauranteId, formaPagamentoModel.getId(), "desassociar"));
            });
        }

        return ResponseEntity.ok(formasPagamentoModel);
    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{formaPagamentoId}")
    public ResponseEntity<Void> vincular(@PathVariable Long restauranteId,
                                                               @PathVariable Long formaPagamentoId) {
        restauranteService.vincularFormaPagamento(restauranteId, formaPagamentoId);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{formaPagamentoId}")
    public ResponseEntity<Void> desvincular(@PathVariable Long restauranteId,
                                                               @PathVariable Long formaPagamentoId) {
        restauranteService.desvincularFormaPagamento(restauranteId, formaPagamentoId);
        return ResponseEntity.noContent().build();
    }
}
