package br.uff.ic.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Espaco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;

    private String nome;

    private String localizacao;

    private int capacidade;

    @ElementCollection //cria uma tabela para armazenar os valores da lista
    private List<String> recursos;

//    @OneToOne(mappedBy = "espaco",cascade = CascadeType.ALL)
//    private Atividade atividade;
}
