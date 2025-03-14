package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.GrupoDTOAssembler;
import com.algaworks.algafood.api.v1.links.AlgaLinks;
import com.algaworks.algafood.api.v1.model.dto.GrupoDTO;
import com.algaworks.algafood.api.v1.openapi.controller.UsuarioGrupoControllerOpenApi;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.core.security.annotations.CheckSecurity;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
    @RequestMapping(path = "/v1/usuarios/{usuarioId}/grupos" , produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UsuarioGruposController implements UsuarioGrupoControllerOpenApi {

    private final UsuarioService usuarioService;

    private final AlgaLinks algaLinks;

    private final GrupoDTOAssembler grupoDTOAssembler;

    private final AlgaSecurity algaSecurity;


    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @Override
    @GetMapping
    public ResponseEntity<CollectionModel<GrupoDTO>> listar(@PathVariable Long usuarioId) {
        Usuario usuario = usuarioService.buscar(usuarioId);

        CollectionModel<GrupoDTO> gruposModel = grupoDTOAssembler.toCollectionModel(usuario.getGrupos())
                .removeLinks();

        if (algaSecurity.podeEditarUsuariosGruposPermissoes()) {
            gruposModel.add(algaLinks.linkToUsuarioGrupoAssociacao(usuarioId, "associar"));

            gruposModel.getContent().forEach(grupoModel -> {
                grupoModel.add(algaLinks.linkToUsuarioGrupoDesassociacao(
                        usuarioId, grupoModel.getId(), "desassociar"));
            });
        }

        return ResponseEntity.ok(gruposModel);

    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @Override
    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        usuarioService.desvincularGrupo(usuarioId, grupoId);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @Override
    @PutMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        usuarioService.vincularGrupo(usuarioId, grupoId);
        return ResponseEntity.noContent().build();
    }


}
