package br.com.aws.campeonato.cbf.repository;

import br.com.aws.campeonato.cbf.domain.Substituicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubstituicaoRepository extends JpaRepository<Substituicao, Integer> {



}
