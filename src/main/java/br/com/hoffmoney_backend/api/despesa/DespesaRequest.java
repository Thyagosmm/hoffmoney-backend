package br.com.hoffmoney_backend.api.despesa;

import java.math.BigDecimal;
import java.util.Date;

public class DespesaRequest {
    private Long usuarioId;
    private String nome;
    private BigDecimal valor;
    private Date dataDeCobranca;

    // Construtor padrão
    public DespesaRequest() {
    }

    // Construtor com parâmetros
    public DespesaRequest(Long usuarioId, String nome, BigDecimal valor, Date dataDeCobranca) {
        this.usuarioId = usuarioId;
        this.nome = nome;
        this.valor = valor;
        this.dataDeCobranca = dataDeCobranca;
    }

    // Getters e Setters
    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Date getDataDeCobranca() {
        return dataDeCobranca;
    }

    public void setDataDeCobranca(Date dataDeCobranca) {
        this.dataDeCobranca = dataDeCobranca;
    }
}