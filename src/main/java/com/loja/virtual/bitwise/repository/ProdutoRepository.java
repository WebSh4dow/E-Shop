package com.loja.virtual.bitwise.repository;

import com.loja.virtual.bitwise.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query(nativeQuery = true, value = "select count(1) > 0 from produto where upper(trim(nome)) = upper(trim(?1))")
    boolean existeProduto(String nome);

    @Query(nativeQuery = true, value = "select a from Produto a where upper (trim(a.nome)) like %?1%")
    List<Produto> findByNome(String nome);
}
