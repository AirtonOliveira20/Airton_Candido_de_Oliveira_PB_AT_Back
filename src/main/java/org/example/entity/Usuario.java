package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;
    private String nomeUsuario;
    private String emailUsuario;
    private String loginUsuario;
    private String senhaUsuario;
    private String tipoUsuario;
    private Boolean status;

}
