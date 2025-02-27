package com.algaworks.algafood.api.v2.links;

import com.algaworks.algafood.api.v2.controller.CidadeControllerV2;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AlgaLinksV2 {
    public Link linkToCidade(Long cidadeId, String rel) {
        return linkTo(methodOn(CidadeControllerV2.class)
                .buscar(cidadeId)).withRel(rel);
    }
    public Link linkToCidades(String rel) {
        return linkTo(CidadeControllerV2.class).withRel(rel);
    }
}
