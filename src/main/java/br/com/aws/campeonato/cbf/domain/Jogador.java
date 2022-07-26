package br.com.aws.campeonato.cbf.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Jogador implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private LocalDate dataNascimento;
    private String pais;

    @ManyToOne
    @JoinColumn(name = "time_id")
    @ToString.Exclude
    private Time time;

    @OneToMany(mappedBy = "jogador")
    @ToString.Exclude
    private List<Transferencia> transferencias;

}
