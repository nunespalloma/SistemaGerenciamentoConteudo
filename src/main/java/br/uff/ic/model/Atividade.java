package br.uff.ic.model;

import jakarta.persistence.Id;
import lombok.*;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Atividade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoAtividade tipo;

    private String nome;

    private String descricao;

    @Temporal(TemporalType.DATE)
    private LocalDate data;

    @Temporal(TemporalType.TIME)
    private LocalTime horarioInicial;

    @Temporal(TemporalType.TIME)
    private LocalTime horarioFinal;

    @ManyToOne
    @JoinColumn(name = "espaco_id")
    private Espaco espaco;

    @ManyToOne
    @JoinColumn(name = "edicao_id")
    private Edicao edicao;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
