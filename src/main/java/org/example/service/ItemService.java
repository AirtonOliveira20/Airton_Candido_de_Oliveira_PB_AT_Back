package org.example.service;

import org.example.entity.Item;
import org.example.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static io.micrometer.common.util.StringUtils.isBlank;

@Service
public class ItemService {

    private ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }

    public List<Item> findAll(){
        return (List<Item>) itemRepository.findAll();
    }

    public Item findById(Integer id){
        validarId(id);
        Optional<Item> item = itemRepository.findById(id);

        if (item.isPresent()) {
            return item.get();
        } else {
            System.out.println("Item não existe");
            return null;
        }
    }

    public Item adicionarItem(Item item){
        try {
            validarItem(item);
            return itemRepository.save(item);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Item alterarItem(Integer id, Item item) {
        validarId(id);

        try {
            validarItem(item);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return null;
        }

        Optional<Item> itemDb = itemRepository.findById(id);

        if (itemDb.isPresent()) {
            item.setIdItem(id);
            return itemRepository.save(item);
        } else {
            System.out.println("Item não existe");
            return null;
        }
    }

    public void deleteItem(Integer id){
        validarId(id);

        Optional<Item> item = itemRepository.findById(id);

        if(item.isPresent()){
            itemRepository.deleteById(id);
            System.out.println("Item excluído com sucesso");
        } else {
            System.out.println("Item não existe");
        }
    }

    private void validarItem(Item item){
        if (item == null) {
            throw new IllegalArgumentException("Os dados do item são obrigatórios.");
        }

        if (isBlank(item.getNomeItem())) {
            throw new IllegalArgumentException("O nome do item é obrigatório.");
        }

        if (isBlank(item.getNumeroPatrimonio())) {
            throw new IllegalArgumentException("O código do patrimônio do item é obrigatório.");
        }

        if (isBlank(item.getModelo())) {
            throw new IllegalArgumentException("O modelo do item é obrigatório.");
        }

        if (isBlank(item.getNumeroSerie())) {
            throw new IllegalArgumentException("O número de série é obrigatório.");
        }

        if (isBlank(item.getStatusItem())) {
            throw new IllegalArgumentException("O status do item é obrigatório.");
        }

        if (item.getIdFornecedor() == null) {
            throw new IllegalArgumentException("O fornecedor é obrigatório.");
        }
    }

    private void validarId(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }
    }
}
