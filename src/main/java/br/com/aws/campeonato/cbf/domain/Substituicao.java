package br.com.aws.campeonato.cbf.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Substituicao implements Serializable {

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
    @JoinColumn(name = "jogador_substituido_id")
    @ToString.Exclude
    private Jogador jogadorSubstituido;

    @ManyToOne
    @JoinColumn(name = "jogador_substituto_id")
    @ToString.Exclude
    private Jogador jogadorSubstituto;

}
