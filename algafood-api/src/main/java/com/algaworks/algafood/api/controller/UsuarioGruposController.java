package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.GrupoDTOAssembler;
import com.algaworks.algafood.api.assembler.UsuarioDTOAssembler;
import com.algaworks.algafood.api.disassembler.UsuarioInputDisassembler;
import com.algaworks.algafood.api.model.dto.GrupoDTO;
import com.algaworks.algafood.api.model.dto.UsuarioDTO;
import com.algaworks.algafood.api.model.input.SenhaInput;
import com.algaworks.algafood.api.model.input.UsuarioComSenhaInput;
import com.algaworks.algafood.api.model.input.UsuarioInput;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.GrupoService;
import com.algaworks.algafood.domain.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
    @RequestMapping("/usuarios/{usuarioId}/grupos")
@RequiredArgsConstructor
public class UsuarioGruposController {

    private final UsuarioService usuarioService;

    private final GrupoService grupoService;

    private final GrupoDTOAssembler grupoDTOAssembler;

    private final UsuarioInputDisassembler usuarioInputDisassembler;

    @GetMapping
    public ResponseEntity<List<GrupoDTO>> listar(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(
                grupoDTOAssembler.toCollectionList(usuarioService.buscar(usuarioId).getGrupos()));
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desvincular(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        usuarioService.desvincularGrupo(usuarioId, grupoId);
    }

    @PutMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void vincular(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        usuarioService.vincularGrupo(usuarioId, grupoId);
    }


}
