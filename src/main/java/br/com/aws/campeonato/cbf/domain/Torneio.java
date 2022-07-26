package br.com.aws.campeonato.cbf.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Torneio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String descricao;

    @OneToMany(mappedBy = "torneio")
    private List<Partida> partidas;

    @ManyToMany(mappedBy = "torneios")
    @ToString.Exclude
    private List<Time> times;

}
