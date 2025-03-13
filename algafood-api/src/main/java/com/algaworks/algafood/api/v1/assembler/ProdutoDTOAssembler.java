package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.controller.RestauranteProdutosController;
import com.algaworks.algafood.api.v1.links.AlgaLinks;
import com.algaworks.algafood.api.v1.model.dto.ProdutoDTO;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ProdutoDTOAssembler extends RepresentationModelAssemblerSupport<Produto, ProdutoDTO> {

    private final ModelMapper modelMapper;

    private final AlgaLinks algaLinks;

    private final AlgaSecurity algaSecurity;

    public ProdutoDTOAssembler(ModelMapper modelMapper, AlgaLinks algaLinks, AlgaSecurity algaSecurity) {
        super(RestauranteProdutosController.class, ProdutoDTO.class);
        this.modelMapper = modelMapper;
        this.algaLinks = algaLinks;
        this.algaSecurity = algaSecurity;
    }

    @Override
    public ProdutoDTO toModel(Produto produto) {
        ProdutoDTO produtoDTO = createModelWithId(
                produto.getId(), produto, produto.getRestaurante().getId());

        modelMapper.map(produto, produtoDTO);

        if (algaSecurity.podeConsultarRestaurantes()) {
            produtoDTO.add(algaLinks.linkToProdutos(produto.getRestaurante().getId(), "produtos"));

            produtoDTO.add(algaLinks.linkToFotoProduto(
                    produto.getRestaurante().getId(), produto.getId(), "foto"));
        }

        return produtoDTO;
    }

}
