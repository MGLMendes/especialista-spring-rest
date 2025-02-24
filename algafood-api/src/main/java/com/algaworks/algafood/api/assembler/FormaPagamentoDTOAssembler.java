package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.FormaPagamentoController;
import com.algaworks.algafood.api.links.AlgaLinks;
import com.algaworks.algafood.api.model.dto.FormaPagamentoDTO;
import com.algaworks.algafood.domain.model.FormaPagamento;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FormaPagamentoDTOAssembler
        extends RepresentationModelAssemblerSupport<FormaPagamento, FormaPagamentoDTO> {

    private final AlgaLinks algaLinks;

    private final ModelMapper modelMapper;

    public FormaPagamentoDTOAssembler(AlgaLinks algaLinks, ModelMapper modelMapper) {
        super(FormaPagamentoController.class, FormaPagamentoDTO.class);
        this.algaLinks = algaLinks;
        this.modelMapper = modelMapper;
    }

    @Override
    public FormaPagamentoDTO toModel(FormaPagamento formaPagamento) {
        FormaPagamentoDTO formaPagamentoModel =
                createModelWithId(formaPagamento.getId(), formaPagamento);

        modelMapper.map(formaPagamento, formaPagamentoModel);

        formaPagamentoModel.add(algaLinks.linkToFormasPagamento("formasPagamento"));

        return formaPagamentoModel;
    }

    @Override
    public CollectionModel<FormaPagamentoDTO> toCollectionModel(Iterable<? extends FormaPagamento> entities) {
        return super.toCollectionModel(entities)
                .add(algaLinks.linkToFormasPagamento());
    }

}
