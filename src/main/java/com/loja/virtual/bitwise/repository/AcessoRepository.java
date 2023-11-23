package com.loja.virtual.bitwise.repository;

import com.loja.virtual.bitwise.model.Acesso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;

@Repository
@Transactional
public interface AcessoRepository extends JpaRepository<Acesso,Long> {
}
