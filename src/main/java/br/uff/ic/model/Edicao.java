package br.uff.ic.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter //criando os metodos gets e os deixando ocultos
@Setter //criando os metodos sets e os deixando ocultos
@NoArgsConstructor //criando o construtor vazio e o deixando oculto
@AllArgsConstructor //criando o construtor com todos os argumentos e o deixando oculto
@Entity //transformando essa classe em uma entidade JPA
public class Edicao {
    @Id
    @GeneratedValue
    private long id;
    private int numero;
    private int ano;
    private Date dataInicial;
    private Date dataFinal;
    private String cidade;
    @OneToOne
    @JoinColumn(name = "id")
    private Evento evento;
    @OneToOne
    @JoinColumn(name = "id")
    private Usuario organizador;
    @OneToMany
    @JoinColumn(name = "id")
    private List<Atividade> atividades;
}
