package br.uff.ic.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Getter //criando os metodos gets e os deixando ocultos
@Setter //criando os metodos sets e os deixando ocultos
@NoArgsConstructor //criando o construtor vazio e o deixando oculto
@AllArgsConstructor //criando o construtor com todos os argumentos e o deixando oculto
@Entity //transformando essa classe em uma entidade JPA
public class Edicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int numero;

    private int ano;

    @Temporal(TemporalType.DATE)
    private Date dataInicial;

    @Temporal(TemporalType.DATE)
    private Date dataFinal;

    private String cidade;

    @OneToOne
    @JoinColumn(name = "evento_id")
    private Evento evento;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario organizador;

    @OneToMany(mappedBy = "edicao", cascade = CascadeType.ALL)//cascade ALL é para trazer todas as atividades sempre
    // que eu fizer busca por uma edição
    private List<Atividade> atividades;
}
