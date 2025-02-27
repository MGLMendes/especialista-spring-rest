package com.algaworks.algafood.api.v1.controller;


import com.algaworks.algafood.api.v1.links.AlgaLinks;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class RootEntryPointController {

    private final AlgaLinks algaLinks;

    @GetMapping
    public RootEntryPointModel rootEntryPointModel() {
        RootEntryPointModel rootEntryPointModel = new RootEntryPointModel();

        rootEntryPointModel.add(
                algaLinks.linkToCozinhas("cozinhas")
        );
        rootEntryPointModel.add(
                algaLinks.linkToCozinhas("gastronomias")
        );
        rootEntryPointModel.add(
                algaLinks.linkToEstados("estados")
        );
        rootEntryPointModel.add(
                algaLinks.linkToRestaurantes("restaurantes")
        );
        rootEntryPointModel.add(
                algaLinks.linkToPedidos("pedidos")
        );
        rootEntryPointModel.add(
                algaLinks.linkToUsuarios("usuarios")
        );
        rootEntryPointModel.add(
                algaLinks.linkToPermissoes("permissoes")
        );
        rootEntryPointModel.add(
                algaLinks.linkToFormasPagamento("formasPagamentos")
        );
        rootEntryPointModel.add(
                algaLinks.linkToCidades("cidades")
        );

        rootEntryPointModel.add(algaLinks.linkToEstatisticas("estatisticas"));
        return rootEntryPointModel;

    }

    public static class RootEntryPointModel extends
            RepresentationModel<RootEntryPointModel> {

    }
}
