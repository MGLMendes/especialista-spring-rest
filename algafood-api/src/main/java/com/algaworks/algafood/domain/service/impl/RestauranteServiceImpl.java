package com.algaworks.algafood.domain.service.impl;

import com.algaworks.algafood.domain.exception.*;
import com.algaworks.algafood.domain.model.*;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestauranteServiceImpl implements RestauranteService {

    public static final String MSG_COZINHA_NAO_ENCONTRADA = "Não existe cadastro de cozinha com o código %d";


    private static final String MSG_RESTAURANTE_NAO_ENCONTRADO
            = "Não existe um cadastro de restaurante com código %d";

    private final RestauranteRepository restauranteRepository;
    private final CozinhaService cozinhaService;
    private final CidadeService cidadeService;
    private final FormaPagamentoService formaPagamentoService;
    private final UsuarioService usuarioService;

    @Override
    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();

        try {
            Cozinha cozinha = cozinhaService.buscar(cozinhaId);
            Cidade cidade = cidadeService.buscar(restaurante.getEndereco().getCidade().getId());
            restaurante.setCozinha(cozinha);
            restaurante.getEndereco().setCidade(cidade);


            return restauranteRepository.save(restaurante);
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(
                    e.getMessage());
        }
    }

    @Override
    public List<Restaurante> listar() {
        return restauranteRepository.findAll();
    }

    @Override
    public Restaurante buscar(Long restauranteId) {
        return restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));
    }

    @Override
    @Transactional
    public void ativar(Long restauranteId) {
        Restaurante restaurante = buscar(restauranteId);

        restaurante.ativar();

    }

    @Override
    @Transactional
    public void inativar(Long restauranteId) {
        Restaurante restaurante = buscar(restauranteId);

        restaurante.inativar();

    }

    @Transactional
    @Override
    public void vincularFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = buscar(restauranteId);
        FormaPagamento formaPagamentoAtual = formaPagamentoService.buscar(formaPagamentoId);
        restaurante.vincular(formaPagamentoAtual);
    }

    @Transactional
    @Override
    public void desvincularFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = buscar(restauranteId);
        FormaPagamento formaPagamento = formaPagamentoService.buscar(formaPagamentoId);
        restaurante.desvincular(formaPagamento);
    }

    @Transactional
    @Override
    public void abrir(Long restauranteId) {
        Restaurante restauranteAtual = buscar(restauranteId);

        restauranteAtual.abrir();
    }

    @Transactional
    @Override
    public void fechar(Long restauranteId) {

        Restaurante restauranteAtual = buscar(restauranteId);

        restauranteAtual.fechar();

    }

    @Transactional
    @Override
    public void ativar(List<Long> restaurantesId) {
        try {
            restaurantesId.forEach(
                    this::ativar
            );
        }catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @Transactional
    @Override
    public void inativar(List<Long> restaurantesId) {
        try {
            restaurantesId.forEach(
                    this::inativar
            );
        } catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @Transactional
    @Override
    public void vincularResponsavel(Long restauranteId, Long usuarioId) {
        Restaurante restaurante = buscar(restauranteId);
        Usuario usuario = usuarioService.buscar(usuarioId);
        restaurante.adicionarResponsavel(usuario);
    }

    @Transactional
    @Override
    public void desvincularResponsavel(Long restauranteId, Long usuarioId) {
        Restaurante restaurante = buscar(restauranteId);
        Usuario usuario = usuarioService.buscar(usuarioId);
        restaurante.removerResponsavel(usuario);
    }
}
