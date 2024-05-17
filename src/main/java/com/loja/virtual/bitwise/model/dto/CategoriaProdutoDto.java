package com.loja.virtual.bitwise.model.dto;


import java.io.Serializable;

public class CategoriaProdutoDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String nomeDesc;
    private PessoaDto empresa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeDesc() {
        return nomeDesc;
    }

    public void setNomeDesc(String nomeDesc) {
        this.nomeDesc = nomeDesc;
    }

    public PessoaDto getEmpresa() {
        return empresa;
    }

    public void setEmpresa(PessoaDto empresa) {
        this.empresa = empresa;
    }
}
