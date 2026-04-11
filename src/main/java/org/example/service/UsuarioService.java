package org.example.service;

import org.example.entity.Usuario;
import org.example.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static io.micrometer.common.util.StringUtils.isBlank;

@Service
public class UsuarioService {

    private final UsuarioRepository UsuarioRepository;

    public UsuarioService(UsuarioRepository UsuarioRepository) {
        this.UsuarioRepository = UsuarioRepository;
    }

    public List<Usuario> findAll() {

        return (List<Usuario>) UsuarioRepository.findAll();
    }

    public Usuario findById(Integer id) {
        validarId(id);
        Optional<Usuario> Usuario = UsuarioRepository.findById(id);
        return Usuario.orElse(null);
    }

    public Usuario adicionarUsuario(Usuario usuario) {
        if (!validarUsuario(usuario)) {
            return null;
        }

        Optional<Usuario> usuarioExistente = UsuarioRepository.findByEmailUsuario(usuario.getEmailUsuario());

        if (usuarioExistente.isPresent()) {
            System.out.println("Já existe um usuário com esse e-mail");
            return null;
        }

        return UsuarioRepository.save(usuario);
    }

    public Usuario alterarUsuario(Integer id, Usuario dados) {
        validarId(id);
        Optional<Usuario> usuarioEncontrado = UsuarioRepository.findById(id);
        if (usuarioEncontrado.isEmpty()) return null;

        Usuario usuario = usuarioEncontrado.get();
        usuario.setNomeUsuario(dados.getNomeUsuario());
        usuario.setEmailUsuario(dados.getEmailUsuario());
        usuario.setLoginUsuario(dados.getLoginUsuario());
        usuario.setSenhaUsuario(dados.getSenhaUsuario());
        usuario.setTipoUsuario(dados.getTipoUsuario());
        usuario.setStatus(dados.getStatus());

        return UsuarioRepository.save(usuario);
    }

    public boolean deleteUsuario(Integer id) {
        validarId(id);
        if (UsuarioRepository.existsById(id)) {
            UsuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Usuario autenticar(String email, String senha) {
        return UsuarioRepository.findByEmailUsuario(email)
                .filter(usuario -> usuario.getSenhaUsuario().equals(senha))
                .orElse(null);
    }

    private boolean validarUsuario(Usuario usuario) {
        if (usuario == null) {
            System.out.println("Os dados do usuário são obrigatórios.");
            return false;
        }

        if (isBlank(usuario.getNomeUsuario())) {
            System.out.println("O nome do usuário é obrigatório.");
            return false;
        }

        if (isBlank(usuario.getEmailUsuario())) {
            System.out.println("O e-mail é obrigatório.");
            return false;
        }

        if (!usuario.getEmailUsuario().contains("@")) {
            System.out.println("E-mail inválido.");
            return false;
        }

        if (isBlank(usuario.getLoginUsuario())) {
            System.out.println("O login do usuário é obrigatório.");
            return false;
        }

        if (isBlank(usuario.getSenhaUsuario())) {
            System.out.println("A senha é obrigatória.");
            return false;
        }

        if (isBlank(usuario.getTipoUsuario())) {
            System.out.println("O tipo do usuário é obrigatório.");
            return false;
        }

        return true;
    }

    private void validarId(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }
    }
}
