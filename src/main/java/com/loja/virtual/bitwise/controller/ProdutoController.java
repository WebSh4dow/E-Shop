package com.loja.virtual.bitwise.controller;

import com.loja.virtual.bitwise.exception.ExceptionErro;
import com.loja.virtual.bitwise.model.CategoriaProduto;
import com.loja.virtual.bitwise.model.Produto;
import com.loja.virtual.bitwise.model.dto.CategoriaProdutoDto;
import com.loja.virtual.bitwise.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping("/buscar-produto/por-nome/{nome}")
    public ResponseEntity<?> buscarProdutoPorNome(@PathVariable String nome){
        List<Produto> produto = produtoRepository.findByNome(nome.toUpperCase());

        if (produto == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(produto);
    }

    @PostMapping("/salvar-produto")
    public ResponseEntity<?> salvarCategoria(@Validated @RequestBody Produto produto) {
        try {

            if (produto.getId() == null) {
                List<Produto> buscarProdutos = produtoRepository.findByNome(produto.getDescricao().toUpperCase());

                if (!buscarProdutos.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Já existe um cadastro de produto com esta descrição...");
                }
            }

            Produto produtoSalvo = produtoRepository.save(produto);
            return ResponseEntity.ok().body(produtoSalvo);

        } catch (ExceptionErro e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar categoria de produto");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Requisição inválida");
        }
    }


}
