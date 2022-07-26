package br.com.aws.campeonato.cbf.service;

import br.com.aws.campeonato.cbf.domain.Jogador;
import br.com.aws.campeonato.cbf.domain.Time;
import br.com.aws.campeonato.cbf.exception.CBFException;
import br.com.aws.campeonato.cbf.repository.JogadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

@Service
public class JogadorService {

    @Autowired
    private JogadorRepository repository;

    @Autowired
    private TimeService timeService;

    public List<Jogador> findAll() {
        return repository.findAll();
    }

    public Jogador findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new CBFException("Não foi localizado o jogador com id " + id + "."));
    }

    public void insert(Jogador jogador) {
        repository.save(jogador);
    }

    public void update(Integer id, Jogador jogador) {
        Jogador jogadorNovo = findById(id);
        jogadorNovo.setNome(jogador.getNome());
        jogadorNovo.setDataNascimento(jogador.getDataNascimento());
        jogadorNovo.setPais(jogador.getPais());

        if (Objects.nonNull(jogador.getTime())) {
            jogadorNovo.setTime(jogador.getTime());
        }

        repository.save(jogadorNovo);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    public Jogador buscarJogadorPorIdTime(Integer idTime, Integer idJogador) {
        return repository.buscarJogadorPorIdTime(idTime, idJogador)
                .orElseThrow(() -> new CBFException("Não foi localizado o jogador " + idJogador + " no time " + idTime + "."));
    }

    public void inserirTimeJogador(Integer idTime, List<Integer> listaIdsJogadores) {
        if (!CollectionUtils.isEmpty(listaIdsJogadores)) {
            Time time = timeService.findById(idTime);
            listaIdsJogadores.forEach(idJogador -> {
                Jogador jogador = findById(idJogador);
                if (Objects.isNull(jogador.getTime())) {
                    jogador.setTime(time);
                    update(jogador.getId(), jogador);
                }
            });
        } else {
            throw new CBFException("Nenhum jogador foi inserido.");
        }

    }

}
