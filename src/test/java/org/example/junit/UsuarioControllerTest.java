package org.example.junit;

import org.example.controller.UsuarioController;
import org.example.entity.Usuario;
import org.example.service.UsuarioService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private MockMvc mockMvc;

    private Usuario criarUsuarioMock() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);
        usuario.setNomeUsuario("Airton");
        usuario.setEmailUsuario("airton@email.com");
        usuario.setLoginUsuario("airton");
        usuario.setSenhaUsuario("123456");
        usuario.setTipoUsuario("ADMIN");
        usuario.setStatus(true);
        return usuario;
    }

    @Test
    @DisplayName("Listar todos os usuários")
    public void deveListarTodosOsUsuarios() throws Exception {
        Usuario usuarioFake = criarUsuarioMock();

        when(usuarioService.findAll()).thenReturn(List.of(usuarioFake));

        mockMvc.perform(get("/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idUsuario").value(1))
                .andExpect(jsonPath("$[0].nomeUsuario").value("Airton"))
                .andExpect(jsonPath("$[0].emailUsuario").value("airton@email.com"));

        verify(usuarioService).findAll();
    }

    @Test
    @DisplayName("Buscar usuário por id")
    public void deveBuscarUsuarioPorId() throws Exception {
        Usuario usuarioFake = criarUsuarioMock();

        when(usuarioService.findById(1)).thenReturn(usuarioFake);

        mockMvc.perform(get("/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUsuario").value(1))
                .andExpect(jsonPath("$.nomeUsuario").value("Airton"))
                .andExpect(jsonPath("$.loginUsuario").value("airton"))
                .andExpect(jsonPath("$.tipoUsuario").value("ADMIN"));

        verify(usuarioService).findById(1);
    }

    @Test
    @DisplayName("Criar usuário")
    public void deveCriarUsuario() throws Exception {
        Usuario usuarioFake = criarUsuarioMock();

        when(usuarioService.adicionarUsuario(any(Usuario.class))).thenReturn(usuarioFake);

        String json = """
        {
            "nomeUsuario": "Airton",
            "emailUsuario": "airton@email.com",
            "loginUsuario": "airton",
            "senhaUsuario": "123456",
            "tipoUsuario": "ADMIN",
            "status": "true"
        }
        """;

        mockMvc.perform(post("/usuarios")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUsuario").value(1))
                .andExpect(jsonPath("$.nomeUsuario").value("Airton"))
                .andExpect(jsonPath("$.emailUsuario").value("airton@email.com"))
                .andExpect(jsonPath("$.tipoUsuario").value("ADMIN"));

        verify(usuarioService).adicionarUsuario(any(Usuario.class));
    }

    @Test
    @DisplayName("Atualizar usuário")
    public void deveAtualizarUsuario() throws Exception {
        Usuario usuarioAtualizado = criarUsuarioMock();
        usuarioAtualizado.setNomeUsuario("Airton Oliveira");
        usuarioAtualizado.setTipoUsuario("OPERADOR");

        when(usuarioService.alterarUsuario(eq(1), any(Usuario.class))).thenReturn(usuarioAtualizado);

        String json = """
        {
            "nomeUsuario": "Airton Oliveira",
            "emailUsuario": "airton@email.com",
            "loginUsuario": "airton",
            "senhaUsuario": "123456",
            "tipoUsuario": "OPERADOR",
            "status": "true"
        }
        """;

        mockMvc.perform(put("/usuarios/1")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUsuario").value(1))
                .andExpect(jsonPath("$.nomeUsuario").value("Airton Oliveira"))
                .andExpect(jsonPath("$.tipoUsuario").value("OPERADOR"));

        verify(usuarioService).alterarUsuario(eq(1), any(Usuario.class));
    }

    @Test
    @DisplayName("Excluir usuário inexistente")
    public void deveExcluirUsuarioQueNaoExiste() throws Exception {
        when(usuarioService.findById(99)).thenReturn(null);

        mockMvc.perform(delete("/usuarios/99"))
                .andExpect(status().isNotFound());

        verify(usuarioService).findById(99);
    }
}