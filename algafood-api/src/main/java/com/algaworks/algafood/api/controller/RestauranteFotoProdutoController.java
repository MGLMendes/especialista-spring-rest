package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.input.FotoProdutoInput;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

@Log4j2
@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteFotoProdutoController {

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void atualizarFoto(@PathVariable Long restauranteId,
                              @PathVariable Long produtoId,
                              FotoProdutoInput produtoInput) {

        var nomeArquivo = UUID.randomUUID().toString() + "_" + produtoInput.getArquivo().getOriginalFilename();

        var arquivoFoto = Path.of("C:/Workspace/especialista-spring-rest/fotos/catalogo", nomeArquivo);

        log.info(produtoInput.getDescricao());
        log.info(arquivoFoto);
        log.info(produtoInput.getArquivo().getContentType());

        try {
            produtoInput.getArquivo().transferTo(arquivoFoto);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
