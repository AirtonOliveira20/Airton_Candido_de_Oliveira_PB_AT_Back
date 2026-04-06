package org.example.controller;

import org.apache.coyote.Response;
import org.example.entity.Fornecedor;
import org.example.service.FornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {

    private FornecedorService fornecedorService;

    public FornecedorController(FornecedorService fornecedorService) { this.fornecedorService =  fornecedorService;}


    @GetMapping
    public ResponseEntity<List<Fornecedor>> listarFornecedores () {
        List<Fornecedor> fornecedores = fornecedorService.findAll();
        if (fornecedores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(fornecedores);

    }


    @GetMapping("{id}")
    public ResponseEntity<?> consultarFornecedor (@PathVariable Integer id){
        Fornecedor fornecedor = fornecedorService.findById(id);
        if(fornecedor == null){
            return new ResponseEntity<>("Erro: fornecedor não encontrado", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(fornecedor, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> excluirFornecedor(@PathVariable Integer id){
        Fornecedor fornecedor = fornecedorService.findById(id);
        if(fornecedor == null){
            return new ResponseEntity<>("Erro: fornecedor não encontrado", HttpStatus.NOT_FOUND);
        }
        fornecedorService.deleteFornecedor(id);
        return new ResponseEntity<>(fornecedor, HttpStatus.OK);
    }
}

