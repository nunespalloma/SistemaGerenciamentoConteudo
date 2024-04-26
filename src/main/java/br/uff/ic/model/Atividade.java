package br.uff.ic.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;

@Getter //criando os metodos gets e os deixando ocultos
@Setter //criando os metodos sets e os deixando ocultos
@NoArgsConstructor //criando o construtor vazio e o deixando oculto
@AllArgsConstructor //criando o construtor com todos os argumentos e o deixando oculto
public class Atividade {
    private long id;
    private String nome;
    private Tipo tipo;
    private String descricao;
    private Date data;
    private Time horarioInicial;
    private Time horarioFinal;
    private Espaco local;
}
