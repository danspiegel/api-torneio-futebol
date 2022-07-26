package br.com.aws.campeonato.cbf.mapper;

import br.com.aws.campeonato.cbf.domain.Time;
import br.com.aws.campeonato.cbf.provider.representation.JogadorResponseRepresentation;
import br.com.aws.campeonato.cbf.provider.representation.TimeRequestRepresentation;
import br.com.aws.campeonato.cbf.provider.representation.TimeResponseRepresentation;
import org.mapstruct.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        componentModel = "spring")
public interface TimeMapper {

    @IterableMapping(qualifiedByName = "TimeResponse")
    List<TimeResponseRepresentation> toListTimeRepresentation(List<Time> listaDomain);

    Time toTimeDomain(TimeRequestRepresentation requestRepresentation);

    @Named("TimeResponse")
    default TimeResponseRepresentation toTimeRepresentation(Time timeDomain) {
        TimeResponseRepresentation timeRep = new TimeResponseRepresentation();
        timeRep.setId(timeDomain.getId());
        timeRep.setNome(timeDomain.getNome());
        timeRep.setLocalidade(timeDomain.getLocalidade());
        List<JogadorResponseRepresentation> listaJogadoresRep = new ArrayList<>();
        timeDomain.getJogadores().forEach(jogador -> {
            JogadorResponseRepresentation jogadorRep = new JogadorResponseRepresentation();
            jogadorRep.setId(jogador.getId());
            jogadorRep.setNome(jogador.getNome());
            jogadorRep.setDataNascimento(jogador.getDataNascimento().format(DateTimeFormatter.ISO_LOCAL_DATE));
            jogadorRep.setPais(jogador.getPais());
            listaJogadoresRep.add(jogadorRep);
        });
        timeRep.setJogadores(listaJogadoresRep);
        return timeRep;
    }

}
