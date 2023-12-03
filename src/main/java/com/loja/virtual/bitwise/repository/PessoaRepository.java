package com.loja.virtual.bitwise.repository;

import com.loja.virtual.bitwise.model.PessoaJuridica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PessoaRepository extends JpaRepository<PessoaJuridica, Long> {
    @Query(value = "select pj from PessoaJuridica pj where pj.cnpj= ?1")
    PessoaJuridica existeCnpjCadastrado(String cnpj);
}
