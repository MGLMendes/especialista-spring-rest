package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.PedidoDTOAssembler;
import com.algaworks.algafood.api.v1.assembler.PedidoListaDTOAssembler;
import com.algaworks.algafood.api.v1.disassembler.PedidoInputDisassembler;
import com.algaworks.algafood.api.v1.model.dto.PedidoDTO;
import com.algaworks.algafood.api.v1.model.dto.PedidoListaDTO;
import com.algaworks.algafood.api.v1.model.input.PedidoInput;
import com.algaworks.algafood.api.v1.openapi.controller.PedidoControllerOpenApi;
import com.algaworks.algafood.core.data.PageWrapper;
import com.algaworks.algafood.core.data.PageableTranslator;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import com.algaworks.algafood.domain.service.FluxoPedidoService;
import com.algaworks.algafood.domain.service.PedidoService;
import com.algaworks.algafood.infra.factory.PedidoSpecs;
import com.google.common.collect.ImmutableMap;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PedidoController implements PedidoControllerOpenApi {

    private final FluxoPedidoService fluxoPedidoService;

    private final PedidoService pedidoService;
    
    private final PedidoDTOAssembler pedidoDTOAssembler;

    private final PedidoListaDTOAssembler pedidoListaDTOAssembler;

    private final PedidoInputDisassembler pedidoInputDisassembler;

    private final PagedResourcesAssembler<Pedido> pagedResourcesAssembler;

    private final AlgaSecurity algaSecurity;

    @Override
    @GetMapping
    public ResponseEntity<PagedModel<PedidoListaDTO>> listar(PedidoFilter filtro, Pageable pageable) {

            Pageable pageableTraduzido = traduzirPageable(pageable);

            Page<Pedido> pedidosPage = pedidoService.listarTodos(
                    PedidoSpecs.usandoFiltro(filtro), pageableTraduzido);

            pedidosPage = new PageWrapper<>(pedidosPage, pageable);

            return ResponseEntity.ok(pagedResourcesAssembler.toModel(pedidosPage, pedidoListaDTOAssembler));
    }


    @Override@GetMapping("/{codigoProduto}")
    public ResponseEntity<PedidoDTO> buscar(@PathVariable String codigoProduto) {
        return ResponseEntity.ok(
                pedidoDTOAssembler.toModel(pedidoService.buscar(codigoProduto))
        );
    }


    @Override
    @PostMapping
    public ResponseEntity<PedidoDTO> emitir(@Valid @RequestBody PedidoInput pedidoInput) {
        Pedido novoPedido = pedidoInputDisassembler.toDomainObject(pedidoInput);

        // TODO pegar usuário autenticado
        novoPedido.setCliente(new Usuario());
        novoPedido.getCliente().setId(algaSecurity.getUsuarioId());

        novoPedido = pedidoService.emitir(novoPedido);

        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoDTOAssembler.toModel(novoPedido));
    }


    public Pageable traduzirPageable(Pageable pageable) {
        var mapeamento = ImmutableMap.of(
                "cliente.nome", "cliente.nome",
                "subTotal", "subTotal",
                "codigo", "codigo",
                "nomeRestaurante", "restaurante.nome",
                "valorTotal", "valorTotal"
        );

        return PageableTranslator.translate(pageable, mapeamento);
    }
}
