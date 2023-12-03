package com.loja.virtual.bitwise.repository;

import com.loja.virtual.bitwise.model.Usuario;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface UsuarioRepository extends CrudRepository<Usuario,Long> {
    @Query(value = "select u from Usuario u where u.login = ?1")
    Usuario findUsuarioByLogin(String login);

    @Query(value = "select u from Usuario u where u.pessoa.id = ?1 or u.login = ?2")
    Usuario findUserByPessoa(Long id, String email);

    @Query(value = "select constraint_name from information_schema.constraint_column_usage where table_name = 'usuarios_acesso' and column_name = 'acesso_id' and constraint_name <> 'unique_acesso_user';",nativeQuery = true)
    String consultarConstraintAcesso();

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "insert into usuarios_acesso(usuario_id, acesso_id) values (?1,(select id from Acesso where descricao = 'ROLE_USER'))")
    void insereAcessoUsuarioPessoaJuridica(Long idUsuario);
}
