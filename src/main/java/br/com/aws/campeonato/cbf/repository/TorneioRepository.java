package br.com.aws.campeonato.cbf.repository;

import br.com.aws.campeonato.cbf.domain.Torneio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TorneioRepository extends JpaRepository<Torneio, Integer> {



}
