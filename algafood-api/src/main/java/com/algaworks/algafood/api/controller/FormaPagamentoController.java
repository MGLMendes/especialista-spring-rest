package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.FormaPagamentoDTOAssembler;
import com.algaworks.algafood.api.disassembler.FormaPagamentoInputDisassembler;
import com.algaworks.algafood.api.model.dto.FormaPagamentoDTO;
import com.algaworks.algafood.api.model.input.FormaPagamentoInput;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.service.FormaPagamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/formas-pagamento")
@RequiredArgsConstructor
public class FormaPagamentoController {


    private final FormaPagamentoService formaPagamentoService;

    private final FormaPagamentoInputDisassembler formaPagamentoInputDisassembler;

    private final FormaPagamentoDTOAssembler formaPagamentoDTOAssembler;

    @GetMapping
    public ResponseEntity<List<FormaPagamentoDTO>> listar() {

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(formaPagamentoDTOAssembler.toCollectionList(formaPagamentoService.todasFormasPagamento()));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<FormaPagamentoDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(
                formaPagamentoDTOAssembler.toModel(formaPagamentoService.buscar(id)
        ));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<FormaPagamentoDTO> adicionar(@RequestBody @Valid FormaPagamentoInput formaPagamento) {
        return ResponseEntity.ok(
                formaPagamentoDTOAssembler.toModel(formaPagamentoService.salvar(formaPagamentoInputDisassembler.toDomainObject(formaPagamento))));
    }

    @PutMapping("/{formaPagamentoId}")
    public ResponseEntity<FormaPagamentoDTO> atualizar(@PathVariable Long formaPagamentoId, @RequestBody @Valid FormaPagamentoInput formaPagamento) {
        FormaPagamento formaPagamentoSalva = formaPagamentoService.buscar(formaPagamentoId);
        formaPagamentoInputDisassembler.copyToDomainObject(formaPagamento, formaPagamentoSalva);
        return ResponseEntity.ok(
                formaPagamentoDTOAssembler.toModel(
                        formaPagamentoService.salvar(formaPagamentoSalva)));
    }

    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long formaPagamentoId) {
        formaPagamentoService.remover(formaPagamentoId);
    }
}
