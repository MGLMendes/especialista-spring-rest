package com.algaworks.algafood.domain.service.impl;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.FotoProdutoService;
import com.algaworks.algafood.domain.service.FotoStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FotoProdutoServiceImpl implements FotoProdutoService {

    private final ProdutoRepository produtoRepository;

    private final FotoStorageService fotoStorageService;

    @Transactional
    @Override
    public FotoProduto cadastrarFoto(FotoProduto foto, InputStream dadosArquivo) {

        Long restauranteId = foto.getRestauranteId();
        Long produtoId = foto.getProduto().getId();
        String novoNomeArquivo = fotoStorageService.gerarNovoNomeArquivo(foto.getNomeArquivo());

        Optional<FotoProduto> fotoById = produtoRepository.findFotoById(restauranteId, produtoId);

        fotoById.ifPresent(produtoRepository::delete);

        foto.setNomeArquivo(novoNomeArquivo);
        foto = produtoRepository.save(foto);
        produtoRepository.flush();


        FotoStorageService.NovaFoto novaFoto = FotoStorageService.NovaFoto.builder()
                .inputStream(dadosArquivo)
                .nomeArquivo(foto.getNomeArquivo())
                .build();

        fotoStorageService.armazenar(novaFoto);
        
        return foto;
    }
}
