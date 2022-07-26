package br.com.aws.campeonato.cbf.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Time implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String localidade;

    @OneToMany(mappedBy = "time")
    @ToString.Exclude
    private List<Jogador> jogadores;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "time_torneio",
            joinColumns = @JoinColumn(name = "time_id"),
            inverseJoinColumns = @JoinColumn(name = "torneio_id"),
            uniqueConstraints = {@UniqueConstraint(
                    columnNames = {"time_id", "torneio_id"})})
    @ToString.Exclude
    private List<Torneio> torneios;

}
