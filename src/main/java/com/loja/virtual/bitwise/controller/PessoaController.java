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
    public ResponseEntity<?> salvarPessoaJuridica(@RequestBody PessoaJuridica pessoaJuridica) {

       try {

           if (pessoaJuridica == null) {
               ResponseEntity.status(HttpStatus.NOT_FOUND).build();
           }

           if (pessoaJuridica.getId() == null && pessoaRepository.existeCnpjCadastrado(pessoaJuridica.getCnpj()) != null) {
               throw new ExceptionErro("Já existe um cnpj cadastrado com o número: " + pessoaJuridica.getCnpj());
           }

           if (pessoaJuridica.getId() == null && pessoaRepository.existeInscricaoEstadual(pessoaJuridica.getInscricaoEstadual()) != null) {
               throw new ExceptionErro("Já existe uma inscrição estadual cadastrado com o número: " + pessoaJuridica.getInscricaoEstadual());
           }

           if (pessoaJuridica.getId() == null && pessoaRepository.existeInscricaoMunicipal(pessoaJuridica.getInscricaoMunicipal()) != null) {
               throw new ExceptionErro("Já existe uma inscrição municipal cadastrado com o número: " + pessoaJuridica.getInscricaoMunicipal());
           }

           pessoaJuridica = pessoaUsuarioService.salvarPessoaJuridica(pessoaJuridica);

           return ResponseEntity.status(HttpStatus.CREATED).body(pessoaJuridica);

       } catch (ExceptionErro e) {
           return ResponseEntity.badRequest().body("[Ocorreu um erro na api]: " + e.getMessage());
       }
    }
}
