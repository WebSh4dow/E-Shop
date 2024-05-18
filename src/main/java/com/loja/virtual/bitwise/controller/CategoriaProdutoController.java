package com.loja.virtual.bitwise.controller;

import com.loja.virtual.bitwise.exception.ExceptionErro;
import com.loja.virtual.bitwise.model.Acesso;
import com.loja.virtual.bitwise.model.CategoriaProduto;
import com.loja.virtual.bitwise.model.Pessoa;
import com.loja.virtual.bitwise.model.dto.CategoriaProdutoDto;
import com.loja.virtual.bitwise.model.dto.PessoaDto;
import com.loja.virtual.bitwise.repository.CategoriaProdutoRepository;
import com.loja.virtual.bitwise.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categoria-produto")
public class CategoriaProdutoController {

    @Autowired
    private CategoriaProdutoRepository categoriaProdutoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @GetMapping(value = "/buscarPor/{descricao}")
    public ResponseEntity<?> buscarPorDescricao(@PathVariable String descricao) {
        List<CategoriaProduto> categoriaProdutos = categoriaProdutoRepository.buscarCategoriaProdutoPor(descricao.toUpperCase());
        return new ResponseEntity<>(categoriaProdutos, HttpStatus.OK);
    }

    @DeleteMapping("/remover/{categoriaId}")
    public ResponseEntity<?> removerCategoriaProduto(@PathVariable Long categoriaId) {
        Optional <CategoriaProduto> categoriaProduto = categoriaProdutoRepository.findById(categoriaId);
        categoriaProduto.ifPresent(produto -> categoriaProdutoRepository.delete(produto));

        return new ResponseEntity<>("Categoria de Produto removida com sucesso!!!", HttpStatus.NO_CONTENT);
    }

    @PostMapping("/salvar-categoria-produto")
    public ResponseEntity<?> salvarCategoria(@Validated @RequestBody CategoriaProdutoDto categoriaProdutoDto) {
        try {

            CategoriaProduto categoriaProduto = mapCategoriaProdutoDtoToEntity(categoriaProdutoDto);

            if (categoriaProduto.getId() == null && categoriaProdutoRepository.existeCategoria(categoriaProduto.getNomeDesc())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Existe uma categoria ja cadastrada com esse mesmo nome.");
            }
            CategoriaProduto categoriaProdutoSalvo = categoriaProdutoRepository.save(categoriaProduto);

            CategoriaProdutoDto responseDto = mapEntityToCategoriaProdutoDto(categoriaProdutoSalvo);
            return ResponseEntity.ok(responseDto);

        } catch (ExceptionErro e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar categoria de produto");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Requisição inválida");
        }
    }

    @PutMapping("/atualizar-categoria-produto/{id}")
    public ResponseEntity<?> atualizarCategoria(@PathVariable("id") Long id, @Validated @RequestBody CategoriaProdutoDto categoriaProdutoDto) {
        try {

            Optional<CategoriaProduto> optionalCategoriaProduto = categoriaProdutoRepository.findById(id);

            if (optionalCategoriaProduto.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria de produto não encontrada");
            }

            CategoriaProduto categoriaProduto = mapCategoriaProdutoDtoToEntity(categoriaProdutoDto);
            categoriaProduto.setId(id);

            if (categoriaProdutoRepository.existeOutraCategoriaComMesmoNome(categoriaProduto.getNomeDesc(), id)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Já existe outra categoria cadastrada com esse nome");
            }

            CategoriaProduto categoriaProdutoAtualizada = categoriaProdutoRepository.save(categoriaProduto);

            CategoriaProdutoDto responseDto = mapEntityToCategoriaProdutoDto(categoriaProdutoAtualizada);
            return ResponseEntity.ok(responseDto);

        } catch (ExceptionErro e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar categoria de produto");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Requisição inválida");
        }
    }

    private CategoriaProduto mapCategoriaProdutoDtoToEntity(CategoriaProdutoDto dto) {
        CategoriaProduto categoriaProduto = new CategoriaProduto();
        categoriaProduto.setNomeDesc(dto.getNomeDesc());

        Pessoa empresa = pessoaRepository.findById(dto.getEmpresa().getId()).get();
        categoriaProduto.setEmpresa(empresa);

        return categoriaProduto;
    }

    private CategoriaProdutoDto mapEntityToCategoriaProdutoDto(CategoriaProduto categoriaProduto) {

        CategoriaProdutoDto dto = new CategoriaProdutoDto();
        dto.setId(categoriaProduto.getId());

        PessoaDto empresaDto = new PessoaDto();
        empresaDto.setId(categoriaProduto.getEmpresa().getId());
        empresaDto.setNome(categoriaProduto.getEmpresa().getNome());
        empresaDto.setEmail(categoriaProduto.getEmpresa().getEmail());
        empresaDto.setTelefone(categoriaProduto.getEmpresa().getTelefone());

        dto.setEmpresa(empresaDto);
        dto.setNomeDesc(categoriaProduto.getNomeDesc());
        return dto;
    }
}
