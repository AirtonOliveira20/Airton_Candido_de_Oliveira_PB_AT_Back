package org.example.junit;

import org.example.controller.FornecedorController;
import org.example.entity.Fornecedor;
import org.example.service.FornecedorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FornecedorController.class)
public class FornecedorControllerTest {

    @MockBean
    private FornecedorService fornecedorService;

    @Autowired
    private MockMvc mockMvc;

    private Fornecedor criarFornecedorMock() {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setIdFornecedor(1);
        fornecedor.setNomeFornecedor("Dell");
        fornecedor.setCnpjFornecedor("12.345.678/0001-99");
        fornecedor.setTelefoneFornecedor("21999999999");
        fornecedor.setEmailFornecedor("contato@dell.com");
        return fornecedor;
    }

    @Test
    @DisplayName("Listar todos os fornecedores")
    public void deveListarTodosOsFornecedores() throws Exception {
        Fornecedor fornecedorFake = criarFornecedorMock();

        when(fornecedorService.findAll()).thenReturn(List.of(fornecedorFake));

        mockMvc.perform(get("/fornecedores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idFornecedor").value(1))
                .andExpect(jsonPath("$[0].nomeFornecedor").value("Dell"))
                .andExpect(jsonPath("$[0].emailFornecedor").value("contato@dell.com"));

        verify(fornecedorService).findAll();
    }

    @Test
    @DisplayName("Buscar fornecedor por id")
    public void deveBuscarFornecedorPorId() throws Exception {
        Fornecedor fornecedorFake = criarFornecedorMock();

        when(fornecedorService.findById(1)).thenReturn(fornecedorFake);

        mockMvc.perform(get("/fornecedores/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idFornecedor").value(1))
                .andExpect(jsonPath("$.nomeFornecedor").value("Dell"))
                .andExpect(jsonPath("$.cnpjFornecedor").value("12.345.678/0001-99"));

        verify(fornecedorService).findById(1);
    }



    @Test
    @DisplayName("Excluir fornecedor existente")
    public void deveExcluirFornecedor() throws Exception {
        Fornecedor fornecedorFake = criarFornecedorMock();

        when(fornecedorService.findById(1)).thenReturn(fornecedorFake);

        mockMvc.perform(delete("/fornecedores/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idFornecedor").value(1))
                .andExpect(jsonPath("$.nomeFornecedor").value("Dell"));

        verify(fornecedorService).findById(1);
        verify(fornecedorService).deleteFornecedor(1);
    }

    @Test
    @DisplayName("Excluir fornecedor inexistente")
    public void deveExcluirFornecedorQueNaoExiste() throws Exception {
        when(fornecedorService.findById(99)).thenReturn(null);

        mockMvc.perform(delete("/fornecedores/99"))
                .andExpect(status().isNotFound());

        verify(fornecedorService).findById(99);
    }
}