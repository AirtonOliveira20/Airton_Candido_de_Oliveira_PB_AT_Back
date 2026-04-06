package org.example.repository;

import org.example.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByEmailUsuario(String emailUsuario);

    Optional<Usuario> findByLoginUsuario(String loginUsuario);
}
