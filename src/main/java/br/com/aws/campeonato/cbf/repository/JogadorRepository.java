package br.com.aws.campeonato.cbf.repository;

import br.com.aws.campeonato.cbf.domain.Jogador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JogadorRepository extends JpaRepository<Jogador, Integer> {

    @Query("SELECT j FROM Jogador j WHERE j.id = :idJogador AND j.time.id = :idTime")
    Optional<Jogador> buscarJogadorPorIdTime(@Param("idTime") Integer idTime,
                                             @Param("idJogador") Integer idJogador);

}
