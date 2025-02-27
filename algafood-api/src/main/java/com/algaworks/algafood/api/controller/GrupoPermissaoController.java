package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.GrupoDTOAssembler;
import com.algaworks.algafood.api.assembler.PermissaoDTOAssembler;
import com.algaworks.algafood.api.disassembler.GrupoInputDisassembler;
import com.algaworks.algafood.api.links.AlgaLinks;
import com.algaworks.algafood.api.model.dto.GrupoDTO;
import com.algaworks.algafood.api.model.dto.PermissaoDTO;
import com.algaworks.algafood.api.model.input.GrupoInput;
import com.algaworks.algafood.api.openapi.controller.GrupoPermissaoControllerOpenApi;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.GrupoService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/grupos/{grupoId}/permissoes",
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {

    private final GrupoService grupoService;

    private final PermissaoDTOAssembler permissaoDTOAssembler;

    private final AlgaLinks algaLinks;


    @Override
    @GetMapping
    public ResponseEntity<CollectionModel<PermissaoDTO>> listar(@PathVariable Long grupoId) {
        Grupo grupo = grupoService.buscar(grupoId);

        CollectionModel<PermissaoDTO> permissoesModel
                = permissaoDTOAssembler.toCollectionModel(grupo.getPermissoes())
                .removeLinks()
                .add(algaLinks.linkToGrupoPermissoes(grupoId))
                .add(algaLinks.linkToGrupoPermissaoAssociacao(grupoId, "associar"));

        permissoesModel.getContent().forEach(permissaoModel -> {
            permissaoModel.add(algaLinks.linkToGrupoPermissaoDesassociacao(
                    grupoId, permissaoModel.getId(), "desassociar"));
        });

        return ResponseEntity.ok(permissoesModel);
    }

    @Override
    @DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        grupoService.desassociarPermissao(grupoId, permissaoId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        grupoService.associarPermissao(grupoId, permissaoId);
        return ResponseEntity.noContent().build();
    }
}
