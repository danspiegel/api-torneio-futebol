package br.com.aws.campeonato.cbf.repository;

import br.com.aws.campeonato.cbf.domain.Transferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferenciaRepository extends JpaRepository<Transferencia, Integer> {



}
