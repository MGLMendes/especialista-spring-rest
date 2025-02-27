package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.PermissaoDTOAssembler;
import com.algaworks.algafood.api.v1.model.dto.PermissaoDTO;
import com.algaworks.algafood.api.v1.openapi.controller.PermissaoControllerOpenApi;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/v1/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissaoController implements PermissaoControllerOpenApi {

    private final PermissaoRepository permissaoRepository;
    
    private final PermissaoDTOAssembler permissaoModelAssembler;
    
    @Override
    @GetMapping
    public CollectionModel<PermissaoDTO> listar() {
        List<Permissao> todasPermissoes = permissaoRepository.findAll();
        
        return permissaoModelAssembler.toCollectionModel(todasPermissoes);
    }   
}