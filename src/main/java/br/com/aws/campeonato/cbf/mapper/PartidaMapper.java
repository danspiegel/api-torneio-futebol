package br.com.aws.campeonato.cbf.mapper;

import br.com.aws.campeonato.cbf.dto.PartidaDTO;
import br.com.aws.campeonato.cbf.provider.representation.*;
import org.mapstruct.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        componentModel = "spring")
public interface PartidaMapper {

    @IterableMapping(qualifiedByName = "PartidaResponse")
    List<PartidaResponseRepresentation> toListPartidaRepresentation(List<PartidaDTO> listaPartida);

    @Named("PartidaResponse")
    default PartidaResponseRepresentation toPartidaRepresentation(PartidaDTO partida) {
        PartidaResponseRepresentation partidaRep = new PartidaResponseRepresentation();
        TimeResponseRepresentation timeUmRep = new TimeResponseRepresentation();
        timeUmRep.setId(partida.getTimeUm().getId());
        timeUmRep.setNome(partida.getTimeUm().getNome());
        timeUmRep.setLocalidade(partida.getTimeUm().getLocalidade());
        List<JogadorResponseRepresentation> jogadoresTimeUmRep = new ArrayList<>();
        partida.getTimeUm().getJogadores().forEach(jogador -> {
            JogadorResponseRepresentation jogadorNovo = new JogadorResponseRepresentation();
            TimeResponseRepresentation timeNovo = new TimeResponseRepresentation();
            timeNovo.setId(jogador.getTime().getId());
            timeNovo.setNome(jogador.getTime().getNome());
            timeNovo.setLocalidade(jogador.getTime().getLocalidade());
            jogadorNovo.setId(jogador.getId());
            jogadorNovo.setNome(jogador.getNome());
            jogadorNovo.setDataNascimento(jogador.getDataNascimento().format(DateTimeFormatter.ISO_LOCAL_DATE));
            jogadorNovo.setPais(jogador.getPais());
            jogadorNovo.setTime(timeNovo);
            jogadoresTimeUmRep.add(jogadorNovo);
        });
        timeUmRep.setJogadores(jogadoresTimeUmRep);

        TimeResponseRepresentation timeDoisRep = new TimeResponseRepresentation();
        timeDoisRep.setId(partida.getTimeUm().getId());
        timeDoisRep.setNome(partida.getTimeUm().getNome());
        timeDoisRep.setLocalidade(partida.getTimeUm().getLocalidade());
        List<JogadorResponseRepresentation> jogadoresTimeDoisRep = new ArrayList<>();
        partida.getTimeDois().getJogadores().forEach(jogador -> {
            JogadorResponseRepresentation jogadorNovo = new JogadorResponseRepresentation();
            TimeResponseRepresentation timeNovo = new TimeResponseRepresentation();
            timeNovo.setId(jogador.getTime().getId());
            timeNovo.setNome(jogador.getTime().getNome());
            timeNovo.setLocalidade(jogador.getTime().getLocalidade());
            jogadorNovo.setId(jogador.getId());
            jogadorNovo.setNome(jogador.getNome());
            jogadorNovo.setDataNascimento(jogador.getDataNascimento().format(DateTimeFormatter.ISO_LOCAL_DATE));
            jogadorNovo.setPais(jogador.getPais());
            jogadorNovo.setTime(timeNovo);
            jogadoresTimeDoisRep.add(jogadorNovo);
        });
        timeDoisRep.setJogadores(jogadoresTimeDoisRep);

        partidaRep.setId(partida.getId());
        partidaRep.setTimeUm(timeUmRep);
        partidaRep.setTimeDois(timeDoisRep);
        partidaRep.setDataHoraInicio(partida.getDataHoraInicio());
        partidaRep.setDataHoraFim(partida.getDataHoraFim());
        partidaRep.setAcrescimos(partida.getAcrescimos());
        partidaRep.setIntervalo(partida.getIntervalo());
        partidaRep.setQtdGolsTimeUm(partida.getQtdGolsTimeUm());
        partidaRep.setQtdGolsTimeDois(partida.getQtdGolsTimeDois());

        TorneioResponseRepresentation torneioRep = new TorneioResponseRepresentation();
        torneioRep.setId(partida.getTorneio().getId());
        torneioRep.setDescricao(partida.getTorneio().getDescricao());

        List<TimeResponseRepresentation> timesTorneioRep = new ArrayList<>();

        partida.getTorneio().getTimes().forEach(time -> {
            TimeResponseRepresentation timeTorneioRep = new TimeResponseRepresentation();
            timeTorneioRep.setId(time.getId());
            timeTorneioRep.setNome(time.getNome());
            timeTorneioRep.setLocalidade(time.getLocalidade());
            List<JogadorResponseRepresentation> listaJogadoresNovo = new ArrayList<>();
            time.getJogadores().forEach(jogador -> {
                JogadorResponseRepresentation jogadorNovo = new JogadorResponseRepresentation();
                TimeResponseRepresentation timeNovo = new TimeResponseRepresentation();
                timeNovo.setId(jogador.getTime().getId());
                timeNovo.setNome(jogador.getTime().getNome());
                timeNovo.setLocalidade(jogador.getTime().getLocalidade());
                jogadorNovo.setId(jogador.getId());
                jogadorNovo.setNome(jogador.getNome());
                jogadorNovo.setDataNascimento(jogador.getDataNascimento().format(DateTimeFormatter.ISO_LOCAL_DATE));
                jogadorNovo.setPais(jogador.getPais());
                jogadorNovo.setTime(timeNovo);
                listaJogadoresNovo.add(jogadorNovo);
            });
            timeTorneioRep.setJogadores(listaJogadoresNovo);
            timesTorneioRep.add(timeTorneioRep);
        });

        torneioRep.setTimes(timesTorneioRep);
        partidaRep.setTorneio(torneioRep);
        List<AdvertenciaResponseRepresentation> listaAdvertenciasRep = new ArrayList<>();
        partida.getAdvertencias().forEach(advertencia -> {
            AdvertenciaResponseRepresentation advertenciaRep = new AdvertenciaResponseRepresentation();
            TimeResponseRepresentation timeRep = new TimeResponseRepresentation();
            JogadorResponseRepresentation jogadorRep = new JogadorResponseRepresentation();
            timeRep.setId(advertencia.getTime().getId());
            jogadorRep.setId(advertencia.getJogador().getId());
            advertenciaRep.setId(advertencia.getId());
            advertenciaRep.setTime(timeRep);
            advertenciaRep.setJogador(jogadorRep);
            listaAdvertenciasRep.add(advertenciaRep);
        });
        partidaRep.setAdvertencias(listaAdvertenciasRep);

        List<SubstituicaoResponseRepresentation> listaSubstituicoesRep = new ArrayList<>();

        partida.getSubtituicoes().forEach(substituicao -> {
            SubstituicaoResponseRepresentation substituicaoRep = new SubstituicaoResponseRepresentation();
            TimeResponseRepresentation timeRep = new TimeResponseRepresentation();
            JogadorResponseRepresentation jogadorSubstituido = new JogadorResponseRepresentation();
            JogadorResponseRepresentation jogadorSubstituto = new JogadorResponseRepresentation();
            timeRep.setId(substituicao.getTime().getId());
            jogadorSubstituido.setId(substituicao.getJogadorSubstituido().getId());
            jogadorSubstituto.setId(substituicao.getJogadorSubstituto().getId());
            substituicaoRep.setTime(timeRep);
            substituicaoRep.setJogadorSubstituido(jogadorSubstituido);
            substituicaoRep.setJogadorSubstituto(jogadorSubstituto);
            listaSubstituicoesRep.add(substituicaoRep);
        });

        partidaRep.setSubstituicoes(listaSubstituicoesRep);
        return partidaRep;
    }

}
