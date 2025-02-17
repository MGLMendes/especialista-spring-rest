package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.PedidoDTOAssembler;
import com.algaworks.algafood.api.assembler.PedidoListaDTOAssembler;
import com.algaworks.algafood.api.disassembler.PedidoInputDisassembler;
import com.algaworks.algafood.api.model.dto.PedidoDTO;
import com.algaworks.algafood.api.model.dto.PedidoListaDTO;
import com.algaworks.algafood.api.model.input.PedidoInput;
import com.algaworks.algafood.api.openapi.controller.PedidoControllerOpenApi;
import com.algaworks.algafood.core.data.PageableTranslator;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import com.algaworks.algafood.domain.service.FluxoPedidoService;
import com.algaworks.algafood.domain.service.PedidoService;
import com.algaworks.algafood.infra.factory.PedidoSpecs;
import com.google.common.collect.ImmutableMap;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PedidoController implements PedidoControllerOpenApi {

    private final FluxoPedidoService fluxoPedidoService;

    private final PedidoService pedidoService;
    
    private final PedidoDTOAssembler pedidoDTOAssembler;

    private final PedidoListaDTOAssembler pedidoListaDTOAssembler;

    private final PedidoInputDisassembler pedidoInputDisassembler;

        @GetMapping
    public ResponseEntity<Page<PedidoListaDTO>> listar(PedidoFilter filtro, Pageable pageable) {

        pageable = traduzirPageable(pageable);

        Page<Pedido> pagePedidos = pedidoService.listarTodos(PedidoSpecs.usandoFiltro(filtro), pageable);
        List<PedidoListaDTO> listPedidoListaDTO = pedidoListaDTOAssembler.toCollectionList(pagePedidos.getContent());
        PageImpl<PedidoListaDTO> pagePedidoDTO = new PageImpl<>(listPedidoListaDTO, pageable, pagePedidos.getTotalElements());
        return ResponseEntity.ok(pagePedidoDTO);
    }


    @GetMapping("/{codigoProduto}")
    public ResponseEntity<PedidoDTO> buscar(@PathVariable String codigoProduto) {
        return ResponseEntity.ok(
                pedidoDTOAssembler.toModel(pedidoService.buscar(codigoProduto))
        );
    }


    @PostMapping
    public ResponseEntity<PedidoDTO> emitir(@Valid @RequestBody PedidoInput pedidoInput) {
        Pedido novoPedido = pedidoInputDisassembler.toDomainObject(pedidoInput);

        // TODO pegar usuário autenticado
        novoPedido.setCliente(new Usuario());
        novoPedido.getCliente().setId(1L);

        novoPedido = pedidoService.emitir(novoPedido);

        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoDTOAssembler.toModel(novoPedido));
    }


    @PutMapping("/{codigoPedido}/confirmar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cofirmar(@PathVariable String codigoPedido) {
        fluxoPedidoService.confirmar(codigoPedido);
    }

    @PutMapping("/{codigoPedido}/entregar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void entregar(@PathVariable String codigoPedido) {
        fluxoPedidoService.entregar(codigoPedido);
    }

    @PutMapping("/{codigoPedido}/cancelar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelar(@PathVariable String codigoPedido) {
        fluxoPedidoService.cancelar(codigoPedido);
    }

    public Pageable traduzirPageable(Pageable pageable) {
        var mapeamento = ImmutableMap.of(
                "cliente.nome", "cliente.nome",
                "subTotal", "subTotal",
                "codigo", "codigo",
                "restautante.nome", "restaurante.nome",
                "valorTotal", "valorTotal"
        );

        return PageableTranslator.translate(pageable, mapeamento);
    }
}
