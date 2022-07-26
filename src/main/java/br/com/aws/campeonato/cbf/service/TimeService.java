package br.com.aws.campeonato.cbf.service;

import br.com.aws.campeonato.cbf.domain.Jogador;
import br.com.aws.campeonato.cbf.domain.Time;
import br.com.aws.campeonato.cbf.domain.Torneio;
import br.com.aws.campeonato.cbf.exception.CBFException;
import br.com.aws.campeonato.cbf.repository.TimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

@Service
public class TimeService {

    @Autowired
    private TimeRepository repository;

    public List<Time> findAll() {
        return repository.findAll();
    }

    public Time findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new CBFException("Não foi localizado o time com id " + id + "."));
    }

    public void insert(Time time) {
        repository.save(time);
    }

    public void update(Integer id, Time time) {
        Time timeNovo = findById(id);
        timeNovo.setNome(time.getNome());
        timeNovo.setLocalidade(time.getLocalidade());
        repository.save(timeNovo);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    public void updateTorneiosTime(Time time, Torneio torneio) {
        List<Torneio> torneios = time.getTorneios();
        torneios.add(torneio);
        time.setTorneios(torneios);
        repository.save(time);
    }

}
