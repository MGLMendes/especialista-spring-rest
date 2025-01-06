package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.PedidoDTOAssembler;
import com.algaworks.algafood.api.assembler.PedidoListaDTOAssembler;
import com.algaworks.algafood.api.disassembler.PedidoInputDisassembler;
import com.algaworks.algafood.api.model.dto.PedidoDTO;
import com.algaworks.algafood.api.model.dto.PedidoListaDTO;
import com.algaworks.algafood.api.model.input.PedidoInput;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.PedidoService;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;
    
    private final PedidoDTOAssembler pedidoDTOAssembler;

    private final PedidoListaDTOAssembler pedidoListaDTOAssembler;

    private final PedidoInputDisassembler pedidoInputDisassembler;

//    @GetMapping
//    public ResponseEntity<List<PedidoListaDTO>> listar() {
//        return ResponseEntity.ok(
//                pedidoListaDTOAssembler.toCollectionList(pedidoService.listarTodos())
//        );
//    }

    @GetMapping
    public MappingJacksonValue listar(@RequestParam(required = false) String campos) {
        List<Pedido> pedidos = pedidoService.listarTodos();
        List<PedidoListaDTO> pedidosDTO = pedidoListaDTOAssembler.toCollectionList(pedidos);
        var wrapper = new MappingJacksonValue(pedidosDTO);

        SimpleFilterProvider filterProvider = new SimpleFilterProvider();

        filterProvider.addFilter("pedidosFilter", SimpleBeanPropertyFilter.serializeAll());

        if (StringUtils.isNotBlank(campos)) {
            filterProvider.addFilter("pedidosFilter", SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
        }

        wrapper.setFilters(filterProvider);

        return wrapper;
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
        pedidoService.confirmar(codigoPedido);
    }

    @PutMapping("/{codigoPedido}/entregar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void entregar(@PathVariable String codigoPedido) {
        pedidoService.entregar(codigoPedido);
    }

    @PutMapping("/{codigoPedido}/cancelar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelar(@PathVariable String codigoPedido) {
        pedidoService.cancelar(codigoPedido);
    }
}
