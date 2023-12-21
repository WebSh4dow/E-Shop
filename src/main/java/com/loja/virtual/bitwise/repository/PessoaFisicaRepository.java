package com.loja.virtual.bitwise.repository;

import com.loja.virtual.bitwise.model.PessoaFisica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PessoaFisicaRepository extends JpaRepository<PessoaFisica, Long> {

    @Query(value = "select pf from PessoaFisica pf where upper(trim(pf.nome)) like %?1%")
    List<PessoaFisica> pesquisarNomePessoaFisica(String nome);

    @Query(value = "select pf from PessoaFisica pf where pf.cpf = ?1")
    List<PessoaFisica> pesquisarCpfPessoaFisica(String cpf);
}
