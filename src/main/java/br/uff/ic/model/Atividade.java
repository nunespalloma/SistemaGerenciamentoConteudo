package br.uff.ic.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Getter //criando os metodos gets e os deixando ocultos
@Setter //criando os metodos sets e os deixando ocultos
@NoArgsConstructor //criando o construtor vazio e o deixando oculto
@AllArgsConstructor //criando o construtor com todos os argumentos e o deixando oculto
@Entity //transformando essa classe em uma entidade JPA
public class Atividade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//estrategia para o ID ser criado ordenado ex 1, 2, 3 etc
    private long id;

    private String nome;

    private Tipo tipo;

    private String descricao;

    @Temporal(TemporalType.DATE)//Como a classe Date pega tudo (data, hora, min,etc), usamos esse @Temporal para
    // especificar para o banco exatamente o que eu vou guardar desse dado
    private Date data;

    @Temporal(TemporalType.TIME)
    private Date horarioInicial;

    @Temporal(TemporalType.TIME)
    private Date horarioFinal;

    @OneToOne //relação entre tabelas
    @JoinColumn(name = "espaco_id") //nome da chave estrangeira
    private Espaco local;

    @ManyToOne
    @JoinColumn(name = "edicao_id")
    private Edicao edicao;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
