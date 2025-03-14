package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.PermissaoDTOAssembler;
import com.algaworks.algafood.api.v1.links.AlgaLinks;
import com.algaworks.algafood.api.v1.model.dto.PermissaoDTO;
import com.algaworks.algafood.api.v1.openapi.controller.GrupoPermissaoControllerOpenApi;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.core.security.annotations.CheckSecurity;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.GrupoService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/grupos/{grupoId}/permissoes",
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {

    private final GrupoService grupoService;

    private final PermissaoDTOAssembler permissaoDTOAssembler;

    private final AlgaLinks algaLinks;

    private final AlgaSecurity algaSecurity;

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @Override
    @GetMapping
    public ResponseEntity<CollectionModel<PermissaoDTO>> listar(@PathVariable Long grupoId) {
        Grupo grupo = grupoService.buscar(grupoId);

        CollectionModel<PermissaoDTO> permissoesModel
                = permissaoDTOAssembler.toCollectionModel(grupo.getPermissoes())
                .removeLinks();

        permissoesModel.add(algaLinks.linkToGrupoPermissoes(grupoId));

        if (algaSecurity.podeEditarUsuariosGruposPermissoes()) {
            permissoesModel.add(algaLinks.linkToGrupoPermissaoAssociacao(grupoId, "associar"));

            permissoesModel.getContent().forEach(permissaoModel -> {
                permissaoModel.add(algaLinks.linkToGrupoPermissaoDesassociacao(
                        grupoId, permissaoModel.getId(), "desassociar"));
            });
        }

        return ResponseEntity.ok(permissoesModel);
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @Override
    @DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        grupoService.desassociarPermissao(grupoId, permissaoId);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @Override
    @PutMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        grupoService.associarPermissao(grupoId, permissaoId);
        return ResponseEntity.noContent().build();
    }
}
