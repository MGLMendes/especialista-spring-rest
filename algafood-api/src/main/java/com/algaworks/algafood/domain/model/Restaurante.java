package com.algaworks.algafood.domain.model;

import com.algaworks.algafood.core.validation.annotations.ValorZeroIncluiDescricao;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ValorZeroIncluiDescricao(
        valorField = "taxaFrete",
        descricaoField = "nome",
        descricaoObrigatoria = "Frete Grátis")
@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Restaurante {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String nome;


    @Column(name = "taxa_frete", nullable = false)
    private BigDecimal taxaFrete;


    @ManyToOne
    @JoinColumn(name = "cozinha_id")
    private Cozinha cozinha;

    @Embedded
    private Endereco endereco;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataCadastro;

    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataAtualizacao;

    @OneToMany(mappedBy = "restaurante")
    private List<Produto> produtos;

    @ManyToMany
    @JoinTable(
            name = "restaurante_forma_pagamento",
            joinColumns = @JoinColumn(
                    name = "restaurante_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "forma_pagamento_id"
            )
    )
    private Set<FormaPagamento> formasPagamento = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "restaurante_usuario_responsavel",
            joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id"))
    private Set<Usuario> responsaveis = new HashSet<>();

    private Boolean ativo = Boolean.TRUE;

    private Boolean aberto = Boolean.FALSE;

    public void abrir() {
        setAberto(true);
    }

    public void fechar() {
        setAberto(false);
    }

    public void ativar() {
        setAtivo(true);
    }

    public void inativar() {
        setAtivo(false);
    }

    public void vincular(FormaPagamento formaPagamentoAtual) {
        getFormasPagamento().add(formaPagamentoAtual);
    }

    public void desvincular(FormaPagamento formaPagamento) {
        getFormasPagamento().remove(formaPagamento);
    }

    public void removerResponsavel(Usuario usuario) {
        getResponsaveis().remove(usuario);
    }

    public void adicionarResponsavel(Usuario usuario) {
        getResponsaveis().add(usuario);
    }

    public boolean aceitaFormaPagamento(FormaPagamento formaPagamento) {
        return getFormasPagamento().contains(formaPagamento);
    }

    public boolean naoAceitaFormaPagamento(FormaPagamento formaPagamento) {
        return !aceitaFormaPagamento(formaPagamento);
    }

    public boolean isAberto() {
        return this.aberto;
    }

    public boolean isFechado() {
        return !isAberto();
    }

    public boolean isInativo() {
        return !isAtivo();
    }

    public boolean isAtivo() {
        return this.ativo;
    }

    public boolean aberturaPermitida() {
        return isAtivo() && isFechado();
    }

    public boolean ativacaoPermitida() {
        return isInativo();
    }

    public boolean inativacaoPermitida() {
        return isAtivo();
    }

    public boolean fechamentoPermitido() {
        return isAberto();
    }
}
