package com.loja.virtual.bitwise.controller;

import com.loja.virtual.bitwise.enums.TipoPessoa;
import com.loja.virtual.bitwise.exception.ExceptionErro;
import com.loja.virtual.bitwise.model.Endereco;
import com.loja.virtual.bitwise.model.PessoaFisica;
import com.loja.virtual.bitwise.model.PessoaJuridica;
import com.loja.virtual.bitwise.model.dto.CepDTO;
import com.loja.virtual.bitwise.model.dto.ConsultaCnpjDto;
import com.loja.virtual.bitwise.repository.EnderecoRepository;
import com.loja.virtual.bitwise.repository.PessoaFisicaRepository;
import com.loja.virtual.bitwise.repository.PessoaRepository;
import com.loja.virtual.bitwise.service.PessoaUsuarioService;
import com.loja.virtual.bitwise.util.ValidaCNPJ;
import com.loja.virtual.bitwise.util.ValidaCPF;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaUsuarioService pessoaUsuarioService;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private PessoaFisicaRepository pessoaFisicaRepository;

    @GetMapping("/consultar/pessoa-fisica/por-nome/{nome}")
    public ResponseEntity<List<PessoaFisica>> consultarPessoaFisicaPorNome(@PathVariable String nome) {
        List<PessoaFisica> pessoasFisicas = pessoaFisicaRepository.pesquisarNomePessoaFisica(nome.trim().toUpperCase());
        return new ResponseEntity<List<PessoaFisica>>(pessoasFisicas,HttpStatus.OK);
    }

    @GetMapping("/consultar/pessoa-fisica/por-cpf/{cpf}")
    public ResponseEntity<List<PessoaFisica>> consultarPessoaFisicaPorCpf(@PathVariable String cpf) {
        List<PessoaFisica> pessoasFisicas = pessoaFisicaRepository.pesquisarCpfPessoaFisica(cpf);
        return new ResponseEntity<List<PessoaFisica>>(pessoasFisicas,HttpStatus.OK);
    }

    @GetMapping("/consultar/pessoa-juridica/por-pj/{nome}")
    public ResponseEntity<List<PessoaJuridica>> consultarPessoaJuridicaPorNome(@PathVariable String nome) {
        List<PessoaJuridica> pessoasJuridicas = pessoaRepository.pesquisarNomePessoaJuridica(nome.trim().toUpperCase());
        return new ResponseEntity<List<PessoaJuridica>>(pessoasJuridicas,HttpStatus.OK);
    }

    @GetMapping("/consultar/pessoa-juridica/por-cnpj/{cnpj}")
    public ResponseEntity<List<PessoaJuridica>> consultarPessoaJuridicaPorCnpj(@PathVariable String cnpj) {
        List<PessoaJuridica> pessoasJuridicas = pessoaRepository.existeCnpjsCadastrados(cnpj.trim().toUpperCase());
        return new ResponseEntity<List<PessoaJuridica>>(pessoasJuridicas,HttpStatus.OK);
    }

    @GetMapping("/consultar-cep/{cep}")
    public ResponseEntity<CepDTO> consultaCepPessoa(@PathVariable String cep) {
        CepDTO cepDTO = pessoaUsuarioService.consultaCepPessoa(cep);
        return new ResponseEntity<CepDTO>(cepDTO,HttpStatus.OK);
    }

    @GetMapping("/consultar-cnpj-receita-aws/{cnpj}")
    public ResponseEntity<ConsultaCnpjDto> consultaReceitaWs(@PathVariable String cnpj) {
        ConsultaCnpjDto consultaCnpjReceitaWs = pessoaUsuarioService.consultaCnpjReceitaWs(cnpj);
        return new ResponseEntity<ConsultaCnpjDto>(consultaCnpjReceitaWs,HttpStatus.OK);
    }

    @PostMapping("/salvar/pessoa-juridica")
    public ResponseEntity<?> salvarPessoaJuridica(@RequestBody @Validated PessoaJuridica pessoaJuridica) {
        try {

            if (pessoaJuridica == null) {
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            if (pessoaJuridica.getTipoPessoa() == null) {
                throw new ExceptionErro("Informe o tipo Jurídico ou Fornecedor da loja virtual");

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

            if (!ValidaCNPJ.isCNPJ(pessoaJuridica.getCnpj())) {
                throw new ExceptionErro("CNPJ: " + pessoaJuridica.getCnpj() + " está inválido.");
            }

            if (pessoaJuridica.getId() == null || pessoaJuridica.getId() <= 0) {
                for (int i = 0; i < pessoaJuridica.getEnderecos().size(); i++) {
                    CepDTO cepDTO = pessoaUsuarioService.consultaCepPessoa(pessoaJuridica.getEnderecos().get(i).getCep());

                    pessoaJuridica.getEnderecos().get(i).setBairro(cepDTO.getBairro());
                    pessoaJuridica.getEnderecos().get(i).setCidade(cepDTO.getLocalidade());
                    pessoaJuridica.getEnderecos().get(i).setComplemento(cepDTO.getComplemento());
                    pessoaJuridica.getEnderecos().get(i).setRuaLogra(cepDTO.getLogradouro());
                    pessoaJuridica.getEnderecos().get(i).setUf(cepDTO.getUf());

                }
            } else {
                for (int i = 0; i < pessoaJuridica.getEnderecos().size(); i++) {
                    Endereco enderecos = enderecoRepository.findById(pessoaJuridica.getEnderecos().get(i).getId()).get();
                    if (!enderecos.getCep().equals(pessoaJuridica.getEnderecos().get(i).getCep())) {
                        CepDTO cepDTO = pessoaUsuarioService.consultaCepPessoa(pessoaJuridica.getEnderecos().get(i).getCep());

                        pessoaJuridica.getEnderecos().get(i).setBairro(cepDTO.getBairro());
                        pessoaJuridica.getEnderecos().get(i).setCidade(cepDTO.getLocalidade());
                        pessoaJuridica.getEnderecos().get(i).setComplemento(cepDTO.getComplemento());
                        pessoaJuridica.getEnderecos().get(i).setRuaLogra(cepDTO.getLogradouro());
                        pessoaJuridica.getEnderecos().get(i).setUf(cepDTO.getUf());
                    }
                }
            }

            pessoaJuridica = pessoaUsuarioService.salvarPessoaJuridica(pessoaJuridica);

            return ResponseEntity.status(HttpStatus.CREATED).body(pessoaJuridica);

        } catch (ExceptionErro e) {
            return ResponseEntity.badRequest().body("[Ocorreu um erro na api]: " + e.getMessage());
        }
    }


    @PostMapping("/salvar/pessoa-fisica")
    public ResponseEntity<?> salvarPessoaFisica(@RequestBody @Validated PessoaFisica pessoaFisica) {

        try {

            if (pessoaFisica == null) {
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            if (pessoaFisica.getTipoPessoa() == null) {
                pessoaFisica.setTipoPessoa(String.valueOf(TipoPessoa.valueOf(TipoPessoa.FISICA.name())));
            }

            if (pessoaFisica.getId() == null && pessoaRepository.existeCpfCadastrado(pessoaFisica.getCpf()) != null) {
                throw new ExceptionErro("Já existe um cpf cadastrado com o número: " + pessoaFisica.getCpf());
            }

            if (!ValidaCPF.isCPF(pessoaFisica.getCpf())) {
                throw new ExceptionErro("CPF: " + pessoaFisica.getCpf() + " está inválido.");
            }

            if (pessoaFisica.getId() == null || pessoaFisica.getId() <= 0) {
                for (int i = 0; i < pessoaFisica.getEnderecos().size(); i++) {
                    CepDTO cepDTO = pessoaUsuarioService.consultaCepPessoa(pessoaFisica.getEnderecos().get(i).getCep());

                    pessoaFisica.getEnderecos().get(i).setBairro(cepDTO.getBairro());
                    pessoaFisica.getEnderecos().get(i).setCidade(cepDTO.getLocalidade());
                    pessoaFisica.getEnderecos().get(i).setComplemento(cepDTO.getComplemento());
                    pessoaFisica.getEnderecos().get(i).setRuaLogra(cepDTO.getLogradouro());
                    pessoaFisica.getEnderecos().get(i).setUf(cepDTO.getUf());

                }
            } else {
                for (int i = 0; i < pessoaFisica.getEnderecos().size(); i++) {
                    Endereco enderecos = enderecoRepository.findById(pessoaFisica.getEnderecos().get(i).getId()).get();
                    if (!enderecos.getCep().equals(pessoaFisica.getEnderecos().get(i).getCep())) {
                        CepDTO cepDTO = pessoaUsuarioService.consultaCepPessoa(pessoaFisica.getEnderecos().get(i).getCep());

                        pessoaFisica.getEnderecos().get(i).setBairro(cepDTO.getBairro());
                        pessoaFisica.getEnderecos().get(i).setCidade(cepDTO.getLocalidade());
                        pessoaFisica.getEnderecos().get(i).setComplemento(cepDTO.getComplemento());
                        pessoaFisica.getEnderecos().get(i).setRuaLogra(cepDTO.getLogradouro());
                        pessoaFisica.getEnderecos().get(i).setUf(cepDTO.getUf());
                    }
                }
            }

            pessoaFisica = pessoaUsuarioService.salvarPessoaFisica(pessoaFisica);

            return ResponseEntity.status(HttpStatus.CREATED).body(pessoaFisica);

        } catch (ExceptionErro e) {
            return ResponseEntity.badRequest().body("[Ocorreu um erro na api]: " + e.getMessage());
        }
    }
}
