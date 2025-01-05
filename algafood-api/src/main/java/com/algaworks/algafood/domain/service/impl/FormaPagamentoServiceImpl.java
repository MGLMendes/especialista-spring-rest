package com.algaworks.algafood.domain.service.impl;

import com.algaworks.algafood.domain.exception.FormaPagamentoNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.service.FormaPagamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FormaPagamentoServiceImpl implements FormaPagamentoService {

    public static final String MSG_FORMA_PAGAMENTO_NAO_ENCONTRADA = "Não existe cadastro de forma de pagamento com o código %d";
    public static final String MSG_FORMA_PAGAMENTO_EM_USO = "Forma de pagamento de código %d não pode ser removida, pois está em uso";
    private final FormaPagamentoRepository formaPagamentoRepository;

    @Override
    public List<FormaPagamento> todasFormasPagamento() {
        return formaPagamentoRepository.findAll();
    }

    @Override
    public FormaPagamento buscar(Long id) {
        return formaPagamentoRepository.findById(id).orElseThrow(
                () -> new FormaPagamentoNaoEncontradaException(String.format(
                        MSG_FORMA_PAGAMENTO_NAO_ENCONTRADA, id
                ))
        );
    }

    @Transactional
    @Override
    public FormaPagamento salvar(FormaPagamento formaPagamento) {
        return formaPagamentoRepository.save(formaPagamento);
    }

    @Transactional
    @Override
    public void remover(Long formaPagamentoId) {
        try {
            formaPagamentoRepository.deleteById(formaPagamentoId);
            formaPagamentoRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new FormaPagamentoNaoEncontradaException(formaPagamentoId);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_FORMA_PAGAMENTO_EM_USO, formaPagamentoId));
        }
    }
}
