package org.example.unitarios;

import org.example.entity.Fornecedor;
import org.example.entity.Item;
import org.example.repository.ItemRepository;
import org.example.service.ItemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    private Item criarItemValido() {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setIdFornecedor(1);
        fornecedor.setNomeFornecedor("Dell");

        Item item = new Item();
        item.setIdItem(1);
        item.setNomeItem("Notebook");
        item.setNumeroPatrimonio("0001");
        item.setModelo("Vostro");
        item.setNumeroSerie("SN-001");
        item.setStatusItem("ATIVO");
        item.setVoltagem("220V");
        item.setDescricao("Notebook corporativo com 15 polegadas");
        item.setNumRI("6000");
        item.setIdFornecedor(fornecedor);

        return item;
    }

    @Test
    void deveListarTodosOsItens() {
        Item item = criarItemValido();
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> resultado = itemService.findAll();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Notebook", resultado.get(0).getNomeItem());
        verify(itemRepository, times(1)).findAll();
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoExistiremItens() {
        when(itemRepository.findAll()).thenReturn(List.of());

        List<Item> resultado = itemService.findAll();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(itemRepository, times(1)).findAll();
    }

    @Test
    void deveBuscarItemPorIdQuandoExistir() {
        Item item = criarItemValido();
        when(itemRepository.findById(1)).thenReturn(Optional.of(item));

        Item resultado = itemService.findById(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdItem());
        assertEquals("Notebook", resultado.getNomeItem());
        verify(itemRepository, times(1)).findById(1);
    }

    @Test
    void deveRetornarNullQuandoItemNaoExistir() {
        when(itemRepository.findById(99)).thenReturn(Optional.empty());

        Item resultado = itemService.findById(99);

        assertNull(resultado);
        verify(itemRepository, times(1)).findById(99);
    }

    @Test
    void deveLancarExcecaoQuandoIdForInvalidoNoFindById() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> itemService.findById(0)
        );

        assertEquals("ID inválido.", exception.getMessage());
        verify(itemRepository, never()).findById(any());
    }

    @Test
    void deveAdicionarItemComSucesso() {
        Item item = criarItemValido();
        when(itemRepository.save(item)).thenReturn(item);

        Item resultado = itemService.adicionarItem(item);

        assertNotNull(resultado);
        assertEquals("Notebook", resultado.getNomeItem());
        verify(itemRepository, times(1)).save(item);
    }

    @Test
    void deveRetornarNullAoAdicionarItemInvalido() {
        Item itemInvalido = new Item();

        Item resultado = itemService.adicionarItem(itemInvalido);

        assertNull(resultado);
        verify(itemRepository, never()).save(any(Item.class));
    }

    @Test
    void deveAlterarItemQuandoExistir() {
        Item itemExistente = criarItemValido();
        Item itemAtualizado = criarItemValido();
        itemAtualizado.setNomeItem("Notebook Atualizado");

        when(itemRepository.findById(1)).thenReturn(Optional.of(itemExistente));
        when(itemRepository.save(any(Item.class))).thenReturn(itemAtualizado);

        Item resultado = itemService.alterarItem(1, itemAtualizado);

        assertNotNull(resultado);
        assertEquals("Notebook Atualizado", resultado.getNomeItem());
        verify(itemRepository, times(1)).findById(1);
        verify(itemRepository, times(1)).save(any(Item.class));
    }

    @Test
    void deveRetornarNullAoAlterarItemInexistente() {
        Item item = criarItemValido();
        when(itemRepository.findById(1)).thenReturn(Optional.empty());

        Item resultado = itemService.alterarItem(1, item);

        assertNull(resultado);
        verify(itemRepository, times(1)).findById(1);
        verify(itemRepository, never()).save(any(Item.class));
    }

    @Test
    void deveRetornarNullAoAlterarItemInvalido() {
        Item itemInvalido = new Item();

        Item resultado = itemService.alterarItem(1, itemInvalido);

        assertNull(resultado);
        verify(itemRepository, never()).findById(any());
        verify(itemRepository, never()).save(any(Item.class));
    }

    @Test
    void deveExcluirItemQuandoExistir() {
        Item item = criarItemValido();
        when(itemRepository.findById(1)).thenReturn(Optional.of(item));

        itemService.deleteItem(1);

        verify(itemRepository, times(1)).findById(1);
        verify(itemRepository, times(1)).deleteById(1);
    }

    @Test
    void naoDeveExcluirItemQuandoNaoExistir() {
        when(itemRepository.findById(1)).thenReturn(Optional.empty());

        itemService.deleteItem(1);

        verify(itemRepository, times(1)).findById(1);
        verify(itemRepository, never()).deleteById(any());
    }

    @Test
    void deveLancarExcecaoQuandoIdForInvalidoNoDelete() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> itemService.deleteItem(0)
        );

        assertEquals("ID inválido.", exception.getMessage());
        verify(itemRepository, never()).findById(any());
        verify(itemRepository, never()).deleteById(any());
    }
}
