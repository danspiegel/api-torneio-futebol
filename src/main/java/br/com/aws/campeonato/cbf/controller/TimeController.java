package br.com.aws.campeonato.cbf.controller;

import br.com.aws.campeonato.cbf.domain.Time;
import br.com.aws.campeonato.cbf.mapper.TimeMapper;
import br.com.aws.campeonato.cbf.provider.api.TimesApi;
import br.com.aws.campeonato.cbf.provider.representation.*;
import br.com.aws.campeonato.cbf.service.JogadorService;
import br.com.aws.campeonato.cbf.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/cbf")
public class TimeController extends BaseController implements TimesApi {

    @Autowired
    private TimeService timeService;

    @Autowired
    private JogadorService jogadorService;

    @Autowired
    private TimeMapper mapper;

    @Cacheable(value = "listaDeTimes")
    @Override
    public ResponseEntity<ListaTimesResponseRepresentation> findAllTimes() {
        List<Time> listaJogadores = timeService.findAll();
        return createResponse(new ListaTimesResponseRepresentation()
                .times(mapper.toListTimeRepresentation(listaJogadores))
        );
    }

    @Cacheable(value = "listaDeTimes")
    @Override
    public ResponseEntity<TimeResponseRepresentation> findTimeById(Integer id) {
        Time time = timeService.findById(id);
        return createResponse(mapper.toTimeRepresentation(time));
    }

    @Caching(evict = {
            @CacheEvict(value = "listaDeJogadores", allEntries = true),
            @CacheEvict(value = "listaDePartidas", allEntries = true),
            @CacheEvict(value = "listaDeTimes", allEntries = true)
    })
    @Override
    public ResponseEntity<StandardSuccessRepresentation> deleteTimeById(Integer id) {
        timeService.deleteById(id);
        return createResponseSuccess(HttpStatus.OK, "Time excluído com sucesso.");
    }

    @Caching(evict = {
            @CacheEvict(value = "listaDeJogadores", allEntries = true),
            @CacheEvict(value = "listaDePartidas", allEntries = true),
            @CacheEvict(value = "listaDeTimes", allEntries = true)
    })
    @Override
    public ResponseEntity<StandardSuccessRepresentation> insertTime(TimeRequestRepresentation request) {
        timeService.insert(mapper.toTimeDomain(request));
        return createResponseSuccess(HttpStatus.OK, "Time inserido com sucesso.");
    }

    @Caching(evict = {
            @CacheEvict(value = "listaDeJogadores", allEntries = true),
            @CacheEvict(value = "listaDePartidas", allEntries = true),
            @CacheEvict(value = "listaDeTimes", allEntries = true)
    })
    @Override
    public ResponseEntity<StandardSuccessRepresentation> updateTime(Integer id, TimeRequestRepresentation request) {
        timeService.update(id, mapper.toTimeDomain(request));
        return createResponseSuccess(HttpStatus.OK, "Time atualizado com sucesso.");
    }

    @Caching(evict = {
            @CacheEvict(value = "listaDeJogadores", allEntries = true),
            @CacheEvict(value = "listaDePartidas", allEntries = true),
            @CacheEvict(value = "listaDeTimes", allEntries = true)
    })
    @Override
    public ResponseEntity<StandardSuccessRepresentation> inserirJogadorTime(Integer id, ListaJogadoresRequestRepresentation request) {
        jogadorService.inserirTimeJogador(id, request.getIdsJogadores());
        return createResponseSuccess(HttpStatus.OK, "Jogadores inseridos no time com sucesso.");
    }

}
