package com.loja.virtual.bitwise.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.loja.virtual.bitwise.enums.TipoPessoa;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "pessoa_juridica")
@PrimaryKeyJoinColumn(name = "id")
public class PessoaJuridica extends Pessoa {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    @NotBlank
    private String cnpj;

    @Column(nullable = false)
    @NotBlank
    private String inscricaoEstadual;

    @NotBlank
    private String inscricaoMunicipal;

    @NotBlank
    @Column(nullable = false)
    private String nomeFantasia;

    @NotBlank
    @Column(nullable = false)
    private String razaoSocial;

    @NotBlank
    private String categoria;


    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }

    public String getInscricaoMunicipal() {
        return inscricaoMunicipal;
    }

    public void setInscricaoMunicipal(String inscricaoMunicipal) {
        this.inscricaoMunicipal = inscricaoMunicipal;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
