package org.example.controller;

import org.apache.coyote.Response;
import org.example.entity.Item;
import org.example.entity.Usuario;
import org.example.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/itens")
public class ItemController {

    private ItemService itemService;

    public ItemController(ItemService itemService){
        this.itemService = itemService;
    }

    @GetMapping
    public List<Item> listarItens(){
        return itemService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> buscarPorId(@PathVariable Integer id){

        Item item = itemService.findById(id);
        if (item == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(item);
    }

    @PostMapping
    public Item criarItem(@RequestBody Item item){
        return itemService.adicionarItem(item);
    }

    @PutMapping("/{id}")
    public Item atualizarItem(@PathVariable Integer id, @RequestBody Item item){
        return itemService.alterarItem(id, item);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirItem(@PathVariable Integer id){
        Item item = itemService.findById(id);
        if(item == null){
            return new ResponseEntity<>("Erro: Item não encontrado", HttpStatus.NOT_FOUND);
        }
        itemService.deleteItem(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }
}
