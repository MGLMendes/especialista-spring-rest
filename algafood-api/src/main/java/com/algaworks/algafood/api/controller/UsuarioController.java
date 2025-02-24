package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.UsuarioDTOAssembler;
import com.algaworks.algafood.api.disassembler.UsuarioInputDisassembler;
import com.algaworks.algafood.api.model.dto.UsuarioDTO;
import com.algaworks.algafood.api.model.input.SenhaInput;
import com.algaworks.algafood.api.model.input.UsuarioComSenhaInput;
import com.algaworks.algafood.api.model.input.UsuarioInput;
import com.algaworks.algafood.api.openapi.controller.UsuarioControllerOpenApi;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UsuarioController implements UsuarioControllerOpenApi {

    private final UsuarioService usuarioService;

    private final UsuarioDTOAssembler usuarioDTOAssembler;

    private final UsuarioInputDisassembler usuarioInputDisassembler;

    @GetMapping
    public ResponseEntity<CollectionModel<UsuarioDTO>> listar() {
        return ResponseEntity.ok(
                usuarioDTOAssembler.toCollectionModel(usuarioService.listar()));
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<UsuarioDTO> buscar(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(
                usuarioDTOAssembler.toModel(usuarioService.buscar(usuarioId)));
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> adicionar(@RequestBody @Valid UsuarioComSenhaInput usuarioInput) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                usuarioDTOAssembler.toModel(
                        usuarioService.salvar(
                                usuarioInputDisassembler.toDomainObject(usuarioInput))));
    }

    @PutMapping("/{usuarioId}")
    public ResponseEntity<UsuarioDTO> atualizar(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioInput usuarioInput) {
        Usuario usuarioSalva = usuarioService.buscar(usuarioId);
        usuarioInputDisassembler.copyToDomainObject(usuarioInput, usuarioSalva);
        return ResponseEntity.ok(
                usuarioDTOAssembler.toModel(usuarioService.salvar(usuarioSalva)));
    }

    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long usuarioId) {
        usuarioService.deletar(usuarioId);
    }

    @PutMapping("/{usuarioId}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaInput senha) {
        usuarioService.alterarSenha(usuarioId, senha.getSenhaAtual(), senha.getNovaSenha());
    }
}
