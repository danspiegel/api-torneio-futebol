package br.com.aws.campeonato.cbf.mapper;

import br.com.aws.campeonato.cbf.domain.Jogador;
import br.com.aws.campeonato.cbf.dto.PartidaDTO;
import br.com.aws.campeonato.cbf.provider.representation.JogadorRequestRepresentation;
import br.com.aws.campeonato.cbf.provider.representation.JogadorResponseRepresentation;
import br.com.aws.campeonato.cbf.provider.representation.PartidaResponseRepresentation;
import br.com.aws.campeonato.cbf.provider.representation.TimeResponseRepresentation;
import org.mapstruct.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        componentModel = "spring")
public interface JogadorMapper {

    @IterableMapping(qualifiedByName = "JogadorResponse")
    List<JogadorResponseRepresentation> toListJogadorRepresentation(List<Jogador> listaDomain);

    Jogador toJogadorDomain(JogadorRequestRepresentation requestRepresentation);

    @Named("JogadorResponse")
    default JogadorResponseRepresentation toJogadorRepresentation(Jogador jogadorDomain) {
        JogadorResponseRepresentation jogadorRep = new JogadorResponseRepresentation();
        TimeResponseRepresentation timeRep = new TimeResponseRepresentation();
        jogadorRep.setId(jogadorDomain.getId());
        jogadorRep.setNome(jogadorDomain.getNome());
        jogadorRep.setDataNascimento(jogadorDomain.getDataNascimento().format(DateTimeFormatter.ISO_LOCAL_DATE));
        jogadorRep.setPais(jogadorDomain.getPais());

        if (Objects.nonNull(jogadorDomain.getTime())) {
            timeRep.setId(jogadorDomain.getTime().getId());
            timeRep.setNome(jogadorDomain.getTime().getNome());
            timeRep.setLocalidade(jogadorDomain.getTime().getLocalidade());
        }

        jogadorRep.setTime(timeRep);
        return jogadorRep;
    }

}
