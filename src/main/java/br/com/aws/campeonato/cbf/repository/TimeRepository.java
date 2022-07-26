package br.com.aws.campeonato.cbf.repository;

import br.com.aws.campeonato.cbf.domain.Time;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeRepository extends JpaRepository<Time, Integer> {



}
