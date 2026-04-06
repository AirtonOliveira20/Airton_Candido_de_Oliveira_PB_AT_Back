package org.example.service;

import org.example.entity.Usuario;
import org.example.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<Usuario> Usuario = UsuarioRepository.findById(id);
        return Usuario.orElse(null);
    }

    public Usuario save(Usuario Usuario) {

        return UsuarioRepository.save(Usuario);
    }

    public Usuario update(Integer id, Usuario dados) {
        Optional<Usuario> opt = UsuarioRepository.findById(id);
        if (opt.isEmpty()) return null;

        Usuario usuario = opt.get();
        usuario.setNomeUsuario(dados.getNomeUsuario());
        usuario.setEmailUsuario(dados.getEmailUsuario());
        usuario.setLoginUsuario(dados.getLoginUsuario());
        usuario.setSenhaUsuario(dados.getSenhaUsuario());
        usuario.setTipoUsuario(dados.getTipoUsuario());
        usuario.setStatus(dados.getStatus());

        return UsuarioRepository.save(usuario);
    }

    public boolean deleteUsuario(Integer id) {
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


}
