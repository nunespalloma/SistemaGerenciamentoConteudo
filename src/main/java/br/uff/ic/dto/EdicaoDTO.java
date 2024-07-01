package br.uff.ic.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EdicaoDTO {

    private long id;
    private int numero;
    private int ano;
    private Date dataInicial;
    private Date dataFinal;
    private String cidade;
}
