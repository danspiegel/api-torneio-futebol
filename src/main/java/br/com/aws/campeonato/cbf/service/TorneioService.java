package br.com.aws.campeonato.cbf.service;

import br.com.aws.campeonato.cbf.domain.Time;
import br.com.aws.campeonato.cbf.domain.Torneio;
import br.com.aws.campeonato.cbf.exception.CBFException;
import br.com.aws.campeonato.cbf.repository.TorneioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class TorneioService {

    @Autowired
    private TorneioRepository repository;

    @Autowired
    private TimeService timeService;

    public List<Torneio> findAll() {
        return repository.findAll();
    }

    public Torneio findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new CBFException("Não foi localizado o torneio."));
    }

    public void insert(Torneio torneio, List<Integer> idsTimes) {
        List<Time> listaTimes = new ArrayList<>();
        idsTimes.forEach(idTime -> {
            Time time = timeService.findById(idTime);
            listaTimes.add(time);
        });

        torneio.setTimes(listaTimes);
        repository.save(torneio);

        torneio.getTimes().forEach(time -> {
            timeService.updateTorneiosTime(time, torneio);
        });
    }

    public void update(Torneio torneio) {
        Torneio torneioNovo = findById(torneio.getId());
        torneioNovo.setDescricao(torneio.getDescricao());
        repository.save(torneioNovo);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    public void updateTorneio(Integer idTorneio, String descricao, List<Integer> listaIdsTimes) {
        if (!CollectionUtils.isEmpty(listaIdsTimes)) {
            Torneio torneio = findById(idTorneio);
            torneio.setDescricao(descricao);
            List<Time> listaTimes = new ArrayList<>();

            listaIdsTimes.forEach(idTime -> {
                Time time = timeService.findById(idTime);
                listaTimes.add(time);
            });

            torneio.setTimes(listaTimes);
            repository.save(torneio);

            torneio.getTimes().forEach(time -> {
                timeService.updateTorneiosTime(time, torneio);
            });
        } else {
            throw new CBFException("Não foi adicionado nenhum time ao torneio.");
        }
    }

}
