package com.loja.virtual.bitwise.repository;

import com.loja.virtual.bitwise.model.PessoaJuridica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PessoaRepository extends JpaRepository<PessoaJuridica, Long> {
    @Query(value = "select pj from PessoaJuridica pj where pj.cnpj= ?1")
    PessoaJuridica existeCnpjCadastrado(String cnpj);

    @Query(value = "select pj from PessoaJuridica pj where pj.inscricaoEstadual= ?1")
    PessoaJuridica existeInscricaoEstadual(String inscricaoEstadual);

    @Query(value = "select pj from PessoaJuridica pj where pj.inscricaoMunicipal= ?1")
    PessoaJuridica existeInscricaoMunicipal(String inscricaoMunicipal);

    @Query(value = "select pj from PessoaJuridica pj where pj.email= ?1")
    PessoaJuridica existeEmailCadastrado(String email);
}
