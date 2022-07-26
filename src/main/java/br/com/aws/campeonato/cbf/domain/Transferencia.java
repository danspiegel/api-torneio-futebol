package br.com.aws.campeonato.cbf.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transferencia implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "jogador_id")
    private Jogador jogador;

    @ManyToOne
    @JoinColumn(name = "time_origem_id")
    @ToString.Exclude
    private Time timeOrigem;

    @ManyToOne
    @JoinColumn(name = "time_destino_id")
    @ToString.Exclude
    private Time timeDestino;

    private LocalDate data;
    private BigDecimal valor;

}
