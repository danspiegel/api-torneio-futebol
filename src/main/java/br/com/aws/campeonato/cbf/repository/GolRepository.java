package br.com.aws.campeonato.cbf.repository;

import br.com.aws.campeonato.cbf.domain.Gol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GolRepository extends JpaRepository<Gol, Integer> {



}
