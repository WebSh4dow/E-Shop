package com.loja.virtual.bitwise.service;


import com.loja.virtual.bitwise.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PessoaUsuarioService {

    @Autowired
    private UsuarioRepository usuario;
}
