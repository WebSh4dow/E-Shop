package com.loja.virtual.bitwise.repository;

import com.loja.virtual.bitwise.model.PessoaJuridica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<PessoaJuridica, Long> {
}
