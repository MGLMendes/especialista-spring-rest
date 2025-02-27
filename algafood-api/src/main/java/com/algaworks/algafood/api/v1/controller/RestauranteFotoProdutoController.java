package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.FotoProdutoDTOAssembler;
import com.algaworks.algafood.api.v1.model.dto.FotoProdutoDTO;
import com.algaworks.algafood.api.v1.model.input.FotoProdutoInput;
import com.algaworks.algafood.api.v1.openapi.controller.RestauranteProdutoFotoControllerOpenApi;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.infra.exception.StorageException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.service.FotoProdutoService;
import com.algaworks.algafood.domain.service.FotoStorageService;
import com.algaworks.algafood.domain.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
@RequiredArgsConstructor
public class RestauranteFotoProdutoController implements RestauranteProdutoFotoControllerOpenApi {


    private final ProdutoService produtoService;

    private final FotoProdutoService fotoProdutoService;

    private final FotoStorageService fotoStorageService;

    private final FotoProdutoDTOAssembler fotoProdutoDTOAssembler;

    @Override
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FotoProdutoDTO> atualizarFoto(@PathVariable Long restauranteId,
                                                        @PathVariable Long produtoId,
                                                        @Valid FotoProdutoInput produtoInput,
                                                        @RequestPart(required = true) MultipartFile arquivo) {

        Produto produto = produtoService.buscar(restauranteId, produtoId);

//        MultipartFile arquivo = produtoInput.getArquivo();

        FotoProduto fotoProduto = new FotoProduto();
        fotoProduto.setId(produto.getId());
        fotoProduto.setProduto(produto);
        fotoProduto.setDescricao(produtoInput.getDescricao());
        fotoProduto.setContentType(arquivo.getContentType());
        fotoProduto.setTamanho(arquivo.getSize());
        fotoProduto.setNomeArquivo(arquivo.getOriginalFilename());


        try {
            return ResponseEntity.ok(fotoProdutoDTOAssembler.toDTO(fotoProdutoService.cadastrarFoto(fotoProduto,
                    arquivo.getInputStream())));
        } catch (IOException e) {
            throw new StorageException("Não foi possível salvar o arquivo!", e);
        }
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FotoProdutoDTO> buscar(@PathVariable Long restauranteId,
                                                 @PathVariable Long produtoId) {

        return ResponseEntity.ok(fotoProdutoDTOAssembler.toDTO(fotoProdutoService.buscar(restauranteId, produtoId)));

    }

    @Override
    @GetMapping
    public ResponseEntity<?> servir(@PathVariable Long restauranteId,
                                    @PathVariable Long produtoId,
                                    @RequestHeader(name = "accept") String acceptHeaders)
            throws HttpMediaTypeNotAcceptableException {

        try {
            FotoProduto fotoProduto = fotoProdutoService.buscar(restauranteId, produtoId);

            MediaType mediaType = MediaType.parseMediaType(fotoProduto.getContentType());

            List<MediaType> acceptMediaTypes = MediaType.parseMediaTypes(acceptHeaders);

            fotoStorageService.verificarCompatibilidadeMediaType(mediaType, acceptMediaTypes);

            FotoStorageService.FotoRecuperada fotoRecuperada = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());


            if (fotoRecuperada.temUrl()) {
                return ResponseEntity
                        .status(HttpStatus.FOUND)
                        .header(HttpHeaders.LOCATION, fotoRecuperada.getUrl())
                        .build();
            } else {
                return ResponseEntity.ok()
                        .contentType(mediaType)
                        .body(new InputStreamResource(fotoRecuperada.getInputStream()));
            }
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long restauranteId,
                        @PathVariable Long produtoId) {

        fotoProdutoService.deletarFoto(restauranteId, produtoId);

    }
}
