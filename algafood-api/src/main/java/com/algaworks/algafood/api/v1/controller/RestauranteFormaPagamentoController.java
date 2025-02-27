package com.algaworks.algafood.api.v1.controller;


import com.algaworks.algafood.api.v1.assembler.FormaPagamentoDTOAssembler;
import com.algaworks.algafood.api.v1.links.AlgaLinks;
import com.algaworks.algafood.api.v1.model.dto.FormaPagamentoDTO;
import com.algaworks.algafood.api.v1.openapi.controller.RestauranteFormaPagamentoControllerOpenApi;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/formas-pagamento")
@RequiredArgsConstructor
public class RestauranteFormaPagamentoController implements RestauranteFormaPagamentoControllerOpenApi {

    private final RestauranteService restauranteService;

    private final FormaPagamentoDTOAssembler formaPagamentoDTOAssembler;

    private final AlgaLinks algaLinks;

    @Override
    @GetMapping
    public ResponseEntity<CollectionModel<FormaPagamentoDTO>> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.buscar(restauranteId);
        CollectionModel<FormaPagamentoDTO> collectionModel = formaPagamentoDTOAssembler.toCollectionModel(
                restaurante.getFormasPagamento()).removeLinks().add(algaLinks.linkToRestauranteFormasPagamento(
                        restauranteId
        ));

        collectionModel.getContent().forEach(
                formaPagamentoDTO -> {
                    formaPagamentoDTO
                            .add(algaLinks.lintToRestauranteFormaPagamentoDesassociacao(
                            restauranteId, formaPagamentoDTO.getId(), "desassociar"))
                            .add(algaLinks.linkToRestauranteFormasPagamentoVincular(restauranteId, "associar"));
                }
        );

        return ResponseEntity.ok(collectionModel);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{formaPagamentoId}")
    public ResponseEntity<Void> vincular(@PathVariable Long restauranteId,
                                                               @PathVariable Long formaPagamentoId) {
        restauranteService.vincularFormaPagamento(restauranteId, formaPagamentoId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{formaPagamentoId}")
    public ResponseEntity<Void> desvincular(@PathVariable Long restauranteId,
                                                               @PathVariable Long formaPagamentoId) {
        restauranteService.desvincularFormaPagamento(restauranteId, formaPagamentoId);
        return ResponseEntity.noContent().build();
    }
}
