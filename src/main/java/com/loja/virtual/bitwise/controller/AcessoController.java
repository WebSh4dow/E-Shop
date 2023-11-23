package com.loja.virtual.bitwise.controller;

import com.loja.virtual.bitwise.model.Acesso;
import com.loja.virtual.bitwise.service.AcessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/acessos")
public class AcessoController {

    @Autowired
    private AcessoService acessoService;

    @PostMapping
    public Acesso salvarAcesso(Acesso acesso) {
        return acessoService.salvar(acesso);
    }
}
