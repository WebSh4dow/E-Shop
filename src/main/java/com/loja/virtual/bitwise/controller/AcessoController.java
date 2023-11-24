package com.loja.virtual.bitwise.controller;

import com.loja.virtual.bitwise.model.Acesso;
import com.loja.virtual.bitwise.service.AcessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/acessos")
public class AcessoController {

    @Autowired
    private AcessoService acessoService;

    @PostMapping(value = "/salvarAcesso")
    public ResponseEntity <Acesso> salvarAcesso(@RequestBody Acesso acesso) {
        Acesso acessoSalvo = acessoService.salvar(acesso);
        return new ResponseEntity<Acesso>(acessoSalvo, HttpStatus.CREATED);
    }

    @PostMapping(value = "/removerAcesso")
    public ResponseEntity <?> removerAcesso(@RequestBody Acesso acesso) {
        acessoService.remover(acesso.getId());
        return new ResponseEntity<>("Acesso removido com sucesso!!!",HttpStatus.OK);
    }
}
