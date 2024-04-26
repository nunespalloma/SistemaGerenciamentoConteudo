package br.uff.ic.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter //criando os metodos gets e os deixando ocultos
@Setter //criando os metodos sets e os deixando ocultos
@NoArgsConstructor //criando o construtor vazio e o deixando oculto
@AllArgsConstructor //criando o construtor com todos os argumentos e o deixando oculto
public class Espaco {
    private long id;
    private String nome;
    private String localizacao;
    private int capacidade;
    private List<String> recursos;
}
