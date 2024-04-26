package br.uff.ic.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter //criando os metodos gets e os deixando ocultos
@Setter //criando os metodos sets e os deixando ocultos
@NoArgsConstructor //criando o construtor vazio e o deixando oculto
@AllArgsConstructor //criando o construtor com todos os argumentos e o deixando oculto
public class Edicao {
    private long id;
    private int numero;
    private int ano;
    private Date dataInicial;
    private Date dataFinal;
    private String cidade;
    private Evento evento;
    private Usuario organizador;
    private List<Atividade> atividades;
}
