package com.loja.virtual.bitwise.repository;

import com.loja.virtual.bitwise.model.Acesso;
import com.loja.virtual.bitwise.model.CategoriaProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaProdutoRepository extends JpaRepository<CategoriaProduto, Long> {
    @Query(nativeQuery = true, value = "select count(1) > 0 from categoria_produto where upper(nome_desc) = upper(trim(?1))")
    boolean existeCategoria(String nomeCategoria);

    @Query(nativeQuery = true, value = "select count(1) > 0 from categoria_produto where upper(nome_desc) = upper(trim(?1)) and id <> ?2")
    boolean existeOutraCategoriaComMesmoNome(String nomeCategoria, Long id);

    @Query("select a from CategoriaProduto a where upper(trim(a.nomeDesc)) like %?1%")
    List<CategoriaProduto> buscarCategoriaProdutoPor(String desc);
}
