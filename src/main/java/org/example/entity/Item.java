package org.example.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idItem;

    private String statusItem;

    private String numeroPatrimonio;

    private String nomeItem;

    private String modelo;

    private String numeroSerie;

    private String voltagem;

    private String descricao;

    @Column(name = "num_ri")
    private String numRI;

    @ManyToOne
    @JoinColumn(name = "idFornecedor")
    private Fornecedor idFornecedor;

}
