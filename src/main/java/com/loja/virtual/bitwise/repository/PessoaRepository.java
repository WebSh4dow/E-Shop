package com.loja.virtual.bitwise.repository;

import com.loja.virtual.bitwise.model.PessoaFisica;
import com.loja.virtual.bitwise.model.PessoaJuridica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface PessoaRepository extends JpaRepository<PessoaJuridica, Long> {
    @Query(value = "select pj from PessoaJuridica pj where pj.cnpj= ?1")
    PessoaJuridica existeCnpjCadastrado(String cnpj);

    @Query(value = "select pj from PessoaJuridica pj where pj.cnpj= ?1")
    List<PessoaJuridica> existeCnpjsCadastrados(String cnpj);

    @Query(value = "select pj from PessoaJuridica pj where pj.id = ?1")
    Optional<PessoaJuridica> buscarEmpresaPadrao(Long id);

    @Query(value = "select pf from PessoaFisica pf where pf.cpf = ?1")
    PessoaFisica existeCpfCadastrado(String cpf);

    @Query(value = "select pf from PessoaFisica pf where pf.cpf = ?1")
    List<PessoaFisica> existeCpfsCadastrados(String cpf);

    @Query(value = "select pj from PessoaJuridica pj where upper(trim(pj.nome)) like %?1%")
    List<PessoaJuridica> pesquisarNomePessoaJuridica(String nome);

    @Query(value = "select pj from PessoaJuridica pj where pj.inscricaoEstadual= ?1")
    PessoaJuridica existeInscricaoEstadual(String inscricaoEstadual);

    @Query(value = "select pj from PessoaJuridica pj where pj.inscricaoEstadual= ?1")
    List<PessoaJuridica> existeInscricoesEstaduais(String inscricaoEstadual);

    @Query(value = "select pj from PessoaJuridica pj where pj.inscricaoMunicipal= ?1")
    PessoaJuridica existeInscricaoMunicipal(String inscricaoMunicipal);

    @Query(value = "select pj from PessoaJuridica pj where pj.inscricaoMunicipal= ?1")
    List<PessoaJuridica> existeInscricoesMunicipais(String inscricaoMunicipal);

    @Query(value = "select pj from PessoaJuridica pj where pj.email= ?1")
    PessoaJuridica existeEmailCadastrado(String email);
}
