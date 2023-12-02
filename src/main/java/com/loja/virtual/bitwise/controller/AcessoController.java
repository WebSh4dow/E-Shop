package com.loja.virtual.bitwise.controller;

import com.loja.virtual.bitwise.exception.ExceptionErro;
import com.loja.virtual.bitwise.model.Acesso;
import com.loja.virtual.bitwise.repository.AcessoRepository;
import com.loja.virtual.bitwise.service.AcessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/acessos")
public class AcessoController {

    @Autowired
    private AcessoService acessoService;

    @Autowired
    private AcessoRepository acessoRepository;

    @PostMapping(value = "/salvarAcesso")
    public ResponseEntity<Acesso> salvarAcesso(@RequestBody Acesso acesso) throws ExceptionErro {
        if (acesso.getId() == null) {
            List<Acesso> acessos = acessoRepository.buscarAcessoDesc(acesso.getDescricao().toUpperCase());
            if (acessos.isEmpty())
                throw new ExceptionErro("Já existe um acesso com a descrição: " + acesso.getDescricao());
        }

        Acesso acessoSalvo = acessoService.salvar(acesso);
        return new ResponseEntity<Acesso>(acessoSalvo, HttpStatus.CREATED);
    }

    @PostMapping(value = "/removerAcesso")
    public ResponseEntity<?> removerAcesso(@RequestBody Acesso acesso) {
        acessoService.remover(acesso.getId());
        return new ResponseEntity<>("Acesso removido com sucesso!!!", HttpStatus.OK);
    }

    @DeleteMapping(value = "/removerAcessoPorId/{id}")
    //@Secured({"ROLE_GERENTE","ROLE_ADMIN"})
    public ResponseEntity<?> removerAcessoPorId(@PathVariable Long id) {
        acessoService.remover(id);
        return new ResponseEntity<>("Acesso removido com sucesso!!!", HttpStatus.OK);
    }

    @GetMapping(value = "/obterAcesso/{id}")
    public ResponseEntity<Acesso> obterAcesso(@PathVariable Long id) throws ExceptionErro {
        Acesso acesso = acessoRepository.findById(id).orElse(null);

        if (acesso == null) {
            throw new ExceptionErro("Acesso atual não existe com o código: " + id);
        }

        return new ResponseEntity<Acesso>(acesso, HttpStatus.OK);
    }

    @GetMapping(value = "/buscarPor/{descricao}")
    public ResponseEntity<List<Acesso>> buscarPorDescricao(@PathVariable String descricao) {
        List<Acesso> acessos = acessoRepository.buscarAcessoDesc(descricao);
        return new ResponseEntity<List<Acesso>>(acessos, HttpStatus.OK);
    }
}
