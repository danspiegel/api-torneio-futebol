package br.com.aws.campeonato.cbf.repository;

import br.com.aws.campeonato.cbf.domain.Partida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PartidaRepository extends JpaRepository<Partida, Integer> {

    @Query("SELECT p FROM Partida p WHERE p.id = :idPartida AND p.torneio.id = :idTorneio")
    Optional<Partida> buscarPartidaPorTorneio(@Param("idTorneio") Integer idTorneio,
                                              @Param("idPartida") Integer idPartida);

    @Query("SELECT p FROM Partida p WHERE p.id = :idPartida AND " +
            "(p.timeUm.id = :idTime OR p.timeDois.id = :idTime)")
    Optional<Partida> buscarPartidaPorIdTime(@Param("idPartida") Integer idPartida,
                                        @Param("idTime") Integer idTime);

}
