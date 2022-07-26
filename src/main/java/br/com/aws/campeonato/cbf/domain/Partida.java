package br.com.aws.campeonato.cbf.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Partida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "time_um_id")
    @ToString.Exclude
    private Time timeUm;

    @ManyToOne
    @JoinColumn(name = "time_dois_id")
    @ToString.Exclude
    private Time timeDois;

    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;

    @ManyToOne
    @JoinColumn(name = "torneio_id")
    @ToString.Exclude
    private Torneio torneio;

    private Integer acrescimos;
    private Integer intervalo;

    @OneToMany(mappedBy = "partida")
    @ToString.Exclude
    private List<Gol> gols;

    @OneToMany(mappedBy = "partida")
    @ToString.Exclude
    private List<Advertencia> advertencias;

    @OneToMany(mappedBy = "partida")
    @ToString.Exclude
    private List<Substituicao> substituicoes;

}
