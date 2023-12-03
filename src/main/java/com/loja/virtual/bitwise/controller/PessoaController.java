package com.loja.virtual.bitwise.controller;

import com.loja.virtual.bitwise.exception.ExceptionErro;
import com.loja.virtual.bitwise.model.PessoaJuridica;
import com.loja.virtual.bitwise.repository.PessoaRepository;
import com.loja.virtual.bitwise.service.PessoaUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaUsuarioService pessoaUsuarioService;

    @PostMapping("/salvar/pessoa-juridica")
    public ResponseEntity<PessoaJuridica> salvarPessoaJuridica(@RequestBody PessoaJuridica pessoaJuridica) throws ExceptionErro {

        if (pessoaJuridica == null) {
            throw new ExceptionErro("Pessoa Juridica deve ser informado no cadastro");
        }

        if (pessoaJuridica.getId() == null && pessoaRepository.existeCnpjCadastrado(pessoaJuridica.getCnpj()) != null) {
            throw new ExceptionErro("Já existe um cnpj cadastrado com o número: " + pessoaJuridica.getCnpj());
        }

        pessoaJuridica = pessoaUsuarioService.salvarPessoaJuridica(pessoaJuridica);

        return new ResponseEntity<>(pessoaJuridica, HttpStatus.OK);
    }
}
