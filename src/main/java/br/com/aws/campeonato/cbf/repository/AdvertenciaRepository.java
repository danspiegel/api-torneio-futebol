package br.com.aws.campeonato.cbf.repository;

import br.com.aws.campeonato.cbf.domain.Advertencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertenciaRepository extends JpaRepository<Advertencia, Integer> {



}
