package br.com.aws.campeonato.cbf.domain;

import br.com.aws.campeonato.cbf.enums.TipoCartao;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Advertencia implements Serializable {

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

    @Enumerated(EnumType.STRING)
    private TipoCartao tipoCartao;

}
