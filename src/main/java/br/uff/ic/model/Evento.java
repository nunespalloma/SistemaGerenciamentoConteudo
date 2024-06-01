package br.uff.ic.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Getter //criando os metodos gets e os deixando ocultos
@Setter //criando os metodos sets e os deixando ocultos
@NoArgsConstructor //criando o construtor vazio e o deixando oculto
@AllArgsConstructor //criando o construtor com todos os argumentos e o deixando oculto
@Entity //transformando essa classe em uma entidade JPA
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;

    private String nome;

    private String sigla;

    private String descricao;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL)
    private Edicao edicao;
}
