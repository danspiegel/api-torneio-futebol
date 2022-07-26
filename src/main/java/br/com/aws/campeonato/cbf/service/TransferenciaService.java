package br.com.aws.campeonato.cbf.service;

import br.com.aws.campeonato.cbf.domain.Jogador;
import br.com.aws.campeonato.cbf.domain.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransferenciaService {

    @Autowired
    private JogadorService jogadorService;

    @Autowired
    private TimeService timeService;

    public void updateTime(Integer idJogador, Integer idTime) {
        jogadorService.update(idJogador,
                Jogador.builder()
                        .id(idJogador)
                        .time(timeService.findById(idTime))
                        .build()
        );
    }

}
