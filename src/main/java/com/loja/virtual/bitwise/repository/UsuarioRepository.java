package com.loja.virtual.bitwise.repository;

import com.loja.virtual.bitwise.model.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuario,Long> {
    @Query(value = "select u from Usuario u where u.login = ?1")
    Usuario findUsuarioByLogin(String login);
}
