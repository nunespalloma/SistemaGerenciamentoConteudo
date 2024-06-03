package br.uff.ic.model;

import jakarta.persistence.Id;
import lombok.*;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Atividade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Enumerated(EnumType.STRING)
    private TipoAtividade tipo;

    private String descricao;

    @Temporal(TemporalType.DATE)
    private LocalDate data;

    @Temporal(TemporalType.TIME)
    private LocalTime horarioInicial;

    @Temporal(TemporalType.TIME)
    private LocalTime horarioFinal;

    @OneToOne
    @JoinColumn(name = "espaco_id")
    private Espaco espaco;

    @ManyToOne
    @JoinColumn(name = "edicao_id")
    private Edicao edicao;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
