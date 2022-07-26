package br.com.aws.campeonato.cbf.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Gol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "partida_id")
    @ToString.Exclude
    private Partida partida;

    @ManyToOne
    @JoinColumn(name = "time_id")
    @ToString.Exclude
    private Time time;

    @ManyToOne
    @JoinColumn(name = "jogador_id")
    @ToString.Exclude
    private Jogador jogador;

}
