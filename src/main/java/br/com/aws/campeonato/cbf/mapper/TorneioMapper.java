package br.com.aws.campeonato.cbf.mapper;

import br.com.aws.campeonato.cbf.domain.Advertencia;
import br.com.aws.campeonato.cbf.domain.Substituicao;
import br.com.aws.campeonato.cbf.domain.Torneio;
import br.com.aws.campeonato.cbf.enums.TipoCartao;
import br.com.aws.campeonato.cbf.provider.representation.*;
import org.mapstruct.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        componentModel = "spring")
public interface TorneioMapper {

    @IterableMapping(qualifiedByName = "TorneioResponse")
    List<TorneioResponseRepresentation> toListTorneioRepresentation(List<Torneio> listDomain);

    @Named("TorneioResponse")
    default TorneioResponseRepresentation toTorneioRepresentation(Torneio torneioDomain) {
        TorneioResponseRepresentation torneioRep = new TorneioResponseRepresentation();
        torneioRep.setId(torneioDomain.getId());
        torneioRep.setDescricao(torneioDomain.getDescricao());

        List<TimeResponseRepresentation> listaTimesRep = new ArrayList<>();
        torneioDomain.getTimes().forEach(time -> {
            TimeResponseRepresentation timeRep = new TimeResponseRepresentation();
            timeRep.setId(time.getId());
            timeRep.setNome(time.getNome());
            timeRep.setLocalidade(time.getLocalidade());

            List<JogadorResponseRepresentation> listaJogadoresRep = new ArrayList<>();
            time.getJogadores().forEach(jogador -> {
                JogadorResponseRepresentation jogadorRep = new JogadorResponseRepresentation();
                jogadorRep.setId(jogador.getId());
                jogadorRep.setNome(jogador.getNome());
                jogadorRep.setDataNascimento(jogador.getDataNascimento().format(DateTimeFormatter.ISO_LOCAL_DATE));
                jogadorRep.setPais(jogador.getPais());
                listaJogadoresRep.add(jogadorRep);
            });

            timeRep.setJogadores(listaJogadoresRep);
            listaTimesRep.add(timeRep);
        });

        torneioRep.setTimes(listaTimesRep);
        return torneioRep;
    }

    @Mappings({
            @Mapping(target = "time.id", source = "request.idTime"),
            @Mapping(target = "jogador.id", source = "request.idJogador"),
            @Mapping(target = "tipoCartao", source = "request.codTipoCartao", qualifiedByName = "advertenciaTipoCartaoDomain")
    })
    Advertencia toAdvertenciaDomain(AdvertenciaRequestRepresentation request);

    @Named("advertenciaTipoCartaoDomain")
    default TipoCartao mapAdvertenciaTipoCartaoDomain(Integer codTipoCartao) {
        return TipoCartao.toEnum(codTipoCartao);
    }

    @Mappings({
            @Mapping(target = "time.id", source = "request.idTime"),
            @Mapping(target = "jogadorSubstituido.id", source = "request.idJogadorSubstituido"),
            @Mapping(target = "jogadorSubstituto.id", source = "request.idJogadorSubstituto")
    })
    Substituicao toSubstituicaoDomain(SubstituicaoRequestRepresentation request);

}
