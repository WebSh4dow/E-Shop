package com.loja.virtual.bitwise.service;

import com.loja.virtual.bitwise.model.Acesso;
import com.loja.virtual.bitwise.repository.AcessoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class AcessoService {

    @Autowired
    private AcessoRepository acessoRepository;

    public Acesso salvar(Acesso acesso) {
        return acessoRepository.save(acesso);
    }

}
