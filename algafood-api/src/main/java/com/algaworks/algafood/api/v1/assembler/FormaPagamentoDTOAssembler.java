package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.controller.FormaPagamentoController;
import com.algaworks.algafood.api.v1.links.AlgaLinks;
import com.algaworks.algafood.api.v1.model.dto.FormaPagamentoDTO;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class FormaPagamentoDTOAssembler
        extends RepresentationModelAssemblerSupport<FormaPagamento, FormaPagamentoDTO> {

    private final AlgaLinks algaLinks;

    private final ModelMapper modelMapper;

    private final AlgaSecurity algaSecurity;

    public FormaPagamentoDTOAssembler(AlgaLinks algaLinks, ModelMapper modelMapper, AlgaSecurity algaSecurity) {
        super(FormaPagamentoController.class, FormaPagamentoDTO.class);
        this.algaLinks = algaLinks;
        this.modelMapper = modelMapper;
        this.algaSecurity = algaSecurity;
    }

    @Override
    public FormaPagamentoDTO toModel(FormaPagamento formaPagamento) {
        FormaPagamentoDTO formaPagamentoModel =
                createModelWithId(formaPagamento.getId(), formaPagamento);

        modelMapper.map(formaPagamento, formaPagamentoModel);

        if (algaSecurity.podeConsultarFormasPagamento()) {
            formaPagamentoModel.add(algaLinks.linkToFormasPagamento("formasPagamento"));
        }


        return formaPagamentoModel;
    }

    @Override
    public CollectionModel<FormaPagamentoDTO> toCollectionModel(Iterable<? extends FormaPagamento> entities) {
        CollectionModel<FormaPagamentoDTO> collectionModel = super.toCollectionModel(entities);

        if (algaSecurity.podeConsultarFormasPagamento()) {
            collectionModel.add(algaLinks.linkToFormasPagamento());
        }

        return collectionModel;
    }

}
