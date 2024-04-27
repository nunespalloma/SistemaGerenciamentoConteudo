package br.uff.ic.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.List;

@Getter //criando os metodos gets e os deixando ocultos
@Setter //criando os metodos sets e os deixando ocultos
@NoArgsConstructor //criando o construtor vazio e o deixando oculto
@AllArgsConstructor //criando o construtor com todos os argumentos e o deixando oculto
@Entity //transformando essa classe em uma entidade JPA
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String login;

    private String email;

    private String nome;

    private String afiliacao;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Atividade> atividadesFavoritas;

    @OneToOne(mappedBy = "organizador", cascade = CascadeType.ALL)
    private Edicao edicao;
}
