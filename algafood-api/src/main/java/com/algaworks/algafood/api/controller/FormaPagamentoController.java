package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.FormaPagamentoDTOAssembler;
import com.algaworks.algafood.api.disassembler.FormaPagamentoInputDisassembler;
import com.algaworks.algafood.api.model.dto.FormaPagamentoDTO;
import com.algaworks.algafood.api.model.input.FormaPagamentoInput;
import com.algaworks.algafood.api.openapi.controller.FormaPagamentoControllerOpenApi;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.service.FormaPagamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(path = "/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class FormaPagamentoController implements FormaPagamentoControllerOpenApi {


    private final FormaPagamentoService formaPagamentoService;

    private final FormaPagamentoInputDisassembler formaPagamentoInputDisassembler;

    private final FormaPagamentoDTOAssembler formaPagamentoDTOAssembler;

    @GetMapping
    public ResponseEntity<List<FormaPagamentoDTO>> listar(ServletWebRequest servletWebRequest) {

        ShallowEtagHeaderFilter.disableContentCaching(servletWebRequest.getRequest());

        String eTag = "0";

        OffsetDateTime dataUltimaAtualizacao = formaPagamentoService.getDataAtualizacaoById();

        if (dataUltimaAtualizacao != null) {
            eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
        }

        if (servletWebRequest.checkNotModified(eTag)) {
            return null;
        }


        List<FormaPagamento> formaPagamentos = formaPagamentoService.todasFormasPagamento();

        List<FormaPagamentoDTO> formasPagamentoDTO = formaPagamentoDTOAssembler.toCollectionList(formaPagamentos);


        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .eTag(eTag)
                .body(formasPagamentoDTO);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<FormaPagamentoDTO> buscar(@PathVariable Long  formaPagamentoId,
                                                    ServletWebRequest servletWebRequest) {

        ShallowEtagHeaderFilter.disableContentCaching(servletWebRequest.getRequest());

        String eTag = "0";

        OffsetDateTime dataAtualizacao = formaPagamentoService
                .getDataAtualizacaoById(formaPagamentoId);

        if (dataAtualizacao != null) {
            eTag = String.valueOf(dataAtualizacao.toEpochSecond());
        }

        if (servletWebRequest.checkNotModified(eTag)) {
            return null;
        }

        FormaPagamento formaPagamento = formaPagamentoService.buscar(formaPagamentoId);

        FormaPagamentoDTO formaPagamentoDTO = formaPagamentoDTOAssembler.toModel(formaPagamento);


        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(formaPagamentoDTO);
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
