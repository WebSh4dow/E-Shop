package com.loja.virtual.bitwise.enums;

public enum StatusContaPagar {

    COBRANCA("Pagar"),
    VENCIDA("Vencida"),
    ABERTA("Aberta"),
    ALUGUEL("Aluguel"),
    FUNCIONARIO("Funcionario"),

    NEGOCIADA("Renegociada"),
    QUITADA("Quitada");

    private String descricao;

    private StatusContaPagar(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
