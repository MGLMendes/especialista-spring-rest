package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.FotoProdutoAssembler;
import com.algaworks.algafood.api.model.dto.FotoProdutoDTO;
import com.algaworks.algafood.api.model.input.FotoProdutoInput;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.service.FotoProdutoService;
import com.algaworks.algafood.domain.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

@Log4j2
@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
@RequiredArgsConstructor
public class RestauranteFotoProdutoController {

    private final FotoProdutoService fotoProdutoService;

    private final ProdutoService produtoService;

    private final FotoProdutoAssembler fotoProdutoAssembler;

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FotoProdutoDTO> atualizarFoto(@PathVariable Long restauranteId,
                                                        @PathVariable Long produtoId,
                                                        @Valid FotoProdutoInput produtoInput) {

        Produto produto = produtoService.buscar(restauranteId, produtoId);

        MultipartFile arquivo = produtoInput.getArquivo();

        FotoProduto fotoProduto = new FotoProduto();
        fotoProduto.setId(produto.getId());
        fotoProduto.setProduto(produto);
        fotoProduto.setDescricao(produtoInput.getDescricao());
        fotoProduto.setContentType(arquivo.getContentType());
        fotoProduto.setTamanho(arquivo.getSize());
        fotoProduto.setNomeArquivo(arquivo.getOriginalFilename());


        return ResponseEntity.ok(fotoProdutoAssembler.toDTO(fotoProdutoService.cadastrarFoto(fotoProduto)));
    }
}
