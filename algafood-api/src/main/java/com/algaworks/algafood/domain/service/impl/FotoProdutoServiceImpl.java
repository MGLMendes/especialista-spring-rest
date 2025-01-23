package com.algaworks.algafood.domain.service.impl;

import com.algaworks.algafood.domain.exception.FotoProdutoNaoEncontradaException;
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
        String nomeArquivoExistente = null;


        Optional<FotoProduto> fotoById = produtoRepository.findFotoById(restauranteId, produtoId);


        if (fotoById.isPresent()) {
            nomeArquivoExistente = fotoById.get().getNomeArquivo();
            produtoRepository.delete(fotoById.get());

        }


        foto.setNomeArquivo(novoNomeArquivo);
        foto = produtoRepository.save(foto);
        produtoRepository.flush();


        FotoStorageService.NovaFoto novaFoto = FotoStorageService.NovaFoto.builder()
                .inputStream(dadosArquivo)
                .nomeArquivo(foto.getNomeArquivo())
                .build();


        fotoStorageService.substituir(nomeArquivoExistente, novaFoto);

        return foto;
    }

    @Override
    public FotoProduto buscar(Long restauranteId, Long produtoId) {
        return produtoRepository.findFotoById(restauranteId, produtoId)
                .orElseThrow(() -> new FotoProdutoNaoEncontradaException(restauranteId, produtoId));
    }
}
