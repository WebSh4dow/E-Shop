package com.loja.virtual.bitwise.service;

import com.loja.virtual.bitwise.model.Acesso;
import com.loja.virtual.bitwise.repository.AcessoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AcessoService {

    @Autowired
    private AcessoRepository acessoRepository;

    public Acesso salvar(Acesso acesso) {
        return acessoRepository.save(acesso);
    }

    public void remover(Long id) {
        acessoRepository.deleteById(id);
    }

}
