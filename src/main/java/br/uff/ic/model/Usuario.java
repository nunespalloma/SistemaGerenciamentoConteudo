package br.uff.ic.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter //criando os metodos gets e os deixando ocultos
@Setter //criando os metodos sets e os deixando ocultos
@NoArgsConstructor //criando o construtor vazio e o deixando oculto
@AllArgsConstructor //criando o construtor com todos os argumentos e o deixando oculto
@Entity //transformando essa classe em uma entidade JPA
public class Usuario {
    @Id
    @GeneratedValue
    private long id;
    private String login;
    private String email;
    private String nome;
    private String afiliacao;
    @OneToMany
    @JoinColumn(name = "id")
    private List<Atividade> atividadesFavoritas;
}
