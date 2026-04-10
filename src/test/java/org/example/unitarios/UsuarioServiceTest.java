package org.example.unitarios;

import org.example.entity.Usuario;
import org.example.repository.UsuarioRepository;
import org.example.service.UsuarioService;
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
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario criarUsuarioValido() {
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
    void deveListarTodosOsUsuarios() {
        Usuario usuario = criarUsuarioValido();
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));

        List<Usuario> resultado = usuarioService.findAll();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Airton", resultado.get(0).getNomeUsuario());
    }

    @Test
    void deveBuscarUsuarioPorIdQuandoExistir() {
        Usuario usuario = criarUsuarioValido();
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.findById(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdUsuario());
        assertEquals("Airton", resultado.getNomeUsuario());
    }

    @Test
    void deveRetornarNullQuandoUsuarioNaoExistir() {
        when(usuarioRepository.findById(99)).thenReturn(Optional.empty());

        Usuario resultado = usuarioService.findById(99);

        assertNull(resultado);
    }

    @Test
    void deveLancarExcecaoQuandoIdForInvalido() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> usuarioService.findById(0)
        );

        assertEquals("ID inválido.", exception.getMessage());
    }

    @Test
    void deveAdicionarUsuarioComSucesso() {
        Usuario usuario = criarUsuarioValido();

        when(usuarioRepository.findByEmailUsuario("airton@email.com")).thenReturn(Optional.empty());
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario resultado = usuarioService.adicionarUsuario(usuario);

        assertNotNull(resultado);
        assertEquals("Airton", resultado.getNomeUsuario());
        assertEquals("airton@email.com", resultado.getEmailUsuario());
    }

    @Test
    void deveRetornarNullAoAdicionarUsuarioComEmailDuplicado() {
        Usuario usuario = criarUsuarioValido();

        when(usuarioRepository.findByEmailUsuario("airton@email.com")).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.adicionarUsuario(usuario);

        assertNull(resultado);
    }

    @Test
    void deveRetornarNullAoAdicionarUsuarioInvalido() {
        Usuario usuarioInvalido = new Usuario();

        Usuario resultado = usuarioService.adicionarUsuario(usuarioInvalido);

        assertNull(resultado);
    }

    @Test
    void deveAlterarUsuarioQuandoExistir() {
        Usuario usuarioExistente = criarUsuarioValido();

        Usuario dadosAtualizados = criarUsuarioValido();
        dadosAtualizados.setNomeUsuario("Airton Oliveira");
        dadosAtualizados.setLoginUsuario("airtonoliveira");

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuarioExistente));
        when(usuarioRepository.findByEmailUsuario("airton@email.com")).thenReturn(Optional.of(usuarioExistente));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(dadosAtualizados);

        Usuario resultado = usuarioService.alterarUsuario(1, dadosAtualizados);

        assertNotNull(resultado);
        assertEquals("Airton Oliveira", resultado.getNomeUsuario());
        assertEquals("airtonoliveira", resultado.getLoginUsuario());
    }

    @Test
    void deveRetornarNullAoAlterarUsuarioInexistente() {
        Usuario usuario = criarUsuarioValido();

        when(usuarioRepository.findById(1)).thenReturn(Optional.empty());

        Usuario resultado = usuarioService.alterarUsuario(1, usuario);

        assertNull(resultado);
    }

    @Test
    void deveRetornarNullAoAlterarUsuarioComEmailDeOutroUsuario() {
        Usuario usuario = criarUsuarioValido();

        Usuario outroUsuario = new Usuario();
        outroUsuario.setIdUsuario(2);
        outroUsuario.setEmailUsuario("airton@email.com");

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.findByEmailUsuario("airton@email.com")).thenReturn(Optional.of(outroUsuario));

        Usuario resultado = usuarioService.alterarUsuario(1, usuario);

        assertNull(resultado);
    }

    @Test
    void deveRetornarNullAoAlterarUsuarioInvalido() {
        Usuario usuarioInvalido = new Usuario();

        Usuario resultado = usuarioService.alterarUsuario(1, usuarioInvalido);

        assertNull(resultado);
    }

    @Test
    void deveExcluirUsuarioQuandoExistir() {
        Usuario usuario = criarUsuarioValido();
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));

        usuarioService.deleteUsuario(1);

        verify(usuarioRepository, times(1)).deleteById(1);
    }

    @Test
    void naoDeveExcluirUsuarioQuandoNaoExistir() {
        when(usuarioRepository.findById(1)).thenReturn(Optional.empty());

        usuarioService.deleteUsuario(1);

        verify(usuarioRepository, never()).deleteById(any());
    }

    @Test
    void deveLancarExcecaoAoExcluirComIdInvalido() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> usuarioService.deleteUsuario(0)
        );

        assertEquals("ID inválido.", exception.getMessage());
    }

    @Test
    void deveAutenticarUsuarioComSucesso() {
        Usuario usuario = criarUsuarioValido();
        when(usuarioRepository.findByEmailUsuario("airton@email.com")).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.autenticar("airton@email.com", "123456");

        assertNotNull(resultado);
        assertEquals("airton@email.com", resultado.getEmailUsuario());
    }

    @Test
    void deveRetornarNullQuandoSenhaEstiverIncorreta() {
        Usuario usuario = criarUsuarioValido();
        when(usuarioRepository.findByEmailUsuario("airton@email.com")).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.autenticar("airton@email.com", "senhaErrada");

        assertNull(resultado);
    }

    @Test
    void deveRetornarNullQuandoUsuarioNaoForEncontradoNaAutenticacao() {
        when(usuarioRepository.findByEmailUsuario("airton@email.com")).thenReturn(Optional.empty());

        Usuario resultado = usuarioService.autenticar("airton@email.com", "123456");

        assertNull(resultado);
    }

    @Test
    void deveRetornarNullQuandoEmailDaAutenticacaoForVazio() {
        Usuario resultado = usuarioService.autenticar("", "123456");

        assertNull(resultado);
    }

    @Test
    void deveRetornarNullQuandoSenhaDaAutenticacaoForVazia() {
        Usuario resultado = usuarioService.autenticar("airton@email.com", "");

        assertNull(resultado);
    }
}