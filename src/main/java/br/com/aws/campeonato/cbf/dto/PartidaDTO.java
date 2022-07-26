package br.com.aws.campeonato.cbf.dto;

import br.com.aws.campeonato.cbf.domain.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartidaDTO implements Serializable {

    private Integer id;
    private Time timeUm;
    private Time timeDois;
    private String dataHoraInicio;
    private String dataHoraFim;
    private Torneio torneio;
    private Integer acrescimos;
    private Integer intervalo;
    private Long qtdGolsTimeUm = 0L;
    private Long qtdGolsTimeDois = 0L;

    private List<Advertencia> advertencias;
    private List<Substituicao> subtituicoes;

    public PartidaDTO(Partida partida, Long qtdGolsTimeUm, Long qtdGolsTimeDois) {
        this.id = partida.getId();
        this.timeUm = partida.getTimeUm();
        this.timeDois = partida.getTimeDois();
        this.dataHoraInicio = partida.getDataHoraInicio().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        this.dataHoraFim = partida.getDataHoraFim().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        this.torneio = partida.getTorneio();
        this.acrescimos = partida.getAcrescimos();
        this.intervalo = partida.getIntervalo();
        this.qtdGolsTimeUm = qtdGolsTimeUm;
        this.qtdGolsTimeDois = qtdGolsTimeDois;
        this.advertencias = partida.getAdvertencias();
        this.subtituicoes = partida.getSubstituicoes();
    }

}
