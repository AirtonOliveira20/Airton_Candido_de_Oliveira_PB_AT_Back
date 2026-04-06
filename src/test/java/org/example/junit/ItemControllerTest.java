package org.example.junit;

import org.example.controller.ItemController;
import org.example.entity.Fornecedor;
import org.example.entity.Item;
import org.example.service.ItemService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemController.class)
public class ItemControllerTest {

    @MockBean
    private ItemService itemService;

    @Autowired
    private MockMvc mockMvc;

    private Item criarItemMock() {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setIdFornecedor(1);
        fornecedor.setNomeFornecedor("Dell");

        Item item = new Item();
        item.setIdItem(1);
        item.setNomeItem("Notebook");
        item.setModelo("Vostro");
        item.setNumeroSerie("SN-DELL-001");
        item.setStatusItem("ATIVO");
        item.setNumeroPatrimonio("0001");
        item.setVoltagem("220V");
        item.setDescricao("Notebook para uso administrativo");
        item.setNumRI("6000");
        item.setIdFornecedor(fornecedor);

        return item;
    }

    @Test
    @DisplayName("Listar todos os itens")
    public void deveListarTodosTodosOsItens() throws Exception {
        Item itemFakeParaTeste = criarItemMock();

        when(itemService.findAll()).thenReturn(List.of(itemFakeParaTeste));

        mockMvc.perform(get("/itens"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idItem").value(1))
                .andExpect(jsonPath("$[0].nomeItem").value("Notebook"))
                .andExpect(jsonPath("$[0].modelo").value("Vostro"));

        verify(itemService).findAll();
    }

    @Test
    @DisplayName("Deve buscar item específico por id")
    public void deveBuscarItemPorId() throws Exception {
        Item itemFakeParaTeste = criarItemMock();

        when(itemService.findById(1)).thenReturn(itemFakeParaTeste);

        mockMvc.perform(get("/itens/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idItem").value(1))
                .andExpect(jsonPath("$.nomeItem").value("Notebook"))
                .andExpect(jsonPath("$.modelo").value("Vostro"))
                .andExpect(jsonPath("$.numeroSerie").value("SN-DELL-001"))
                .andExpect(jsonPath("$.statusItem").value("ATIVO"));

        verify(itemService).findById(1);
    }

    @Test
    @DisplayName("Criar item")
    public void deveCriarItem() throws Exception {
        Item itemFakeParaTeste = criarItemMock();

        when(itemService.save(any(Item.class))).thenReturn(itemFakeParaTeste);

        String json = """
        {
            "statusItem": "ATIVO",
            "numeroPatrimonio": "0001",
            "nomeItem": "Notebook",
            "modelo": "Vostro",
            "numeroSerie": "SN-DELL-001",
            "voltagem": "220V",
            "descricao": "Notebook para uso administrativo",
            "numRI": "6000"
        }
        """;

        mockMvc.perform(post("/itens")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idItem").value(1))
                .andExpect(jsonPath("$.nomeItem").value("Notebook"))
                .andExpect(jsonPath("$.modelo").value("Vostro"))
                .andExpect(jsonPath("$.numeroSerie").value("SN-DELL-001"))
                .andExpect(jsonPath("$.statusItem").value("ATIVO"));

        verify(itemService).save(any(Item.class));
    }

    @Test
    @DisplayName("Excluir item existente")
    public void deveExcluirItem() throws Exception {
        Item itemFakeParaTeste = criarItemMock();

        when(itemService.findById(1)).thenReturn(itemFakeParaTeste);

        mockMvc.perform(delete("/itens/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idItem").value(1))
                .andExpect(jsonPath("$.nomeItem").value("Notebook"))
                .andExpect(jsonPath("$.modelo").value("Vostro"));

        verify(itemService).findById(1);
        verify(itemService).deleteItem(1);
    }

    @Test
    @DisplayName("Excluir item inexistente")
    public void deveExcluirItemQueNaoExiste() throws Exception {


        when(itemService.findById(3)).thenReturn(null);

        mockMvc.perform(delete("/itens/3"))
                .andExpect(status().isNotFound());

        verify(itemService).findById(3);
    }

    @Test
    @DisplayName("Atualizar item")
    public void deveAtualizarItem() throws Exception {
        Item itemAtualizado = criarItemMock();
        itemAtualizado.setNomeItem("Notebook Atualizado");
        itemAtualizado.setModelo("Latitude");

        when(itemService.update(eq(1), any(Item.class))).thenReturn(itemAtualizado);

        String json = """
        {
            "statusItem": "ATIVO",
            "numeroPatrimonio": "0001",
            "nomeItem": "Notebook Atualizado",
            "modelo": "Vostro",
            "numeroSerie": "SN-DELL-001",
            "voltagem": "220V",
            "descricao": "Notebook atualizado",
            "numRI": "6000"
        }
        """;

        mockMvc.perform(put("/itens/1")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idItem").value(1))
                .andExpect(jsonPath("$.nomeItem").value("Notebook Atualizado"))
                .andExpect(jsonPath("$.modelo").value("Latitude"));

        verify(itemService).update(eq(1), any(Item.class));
    }

}