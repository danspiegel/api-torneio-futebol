package br.com.aws.campeonato.cbf.controller;

import br.com.aws.campeonato.cbf.domain.Torneio;
import br.com.aws.campeonato.cbf.mapper.TorneioMapper;
import br.com.aws.campeonato.cbf.provider.api.TorneiosApi;
import br.com.aws.campeonato.cbf.provider.representation.*;
import br.com.aws.campeonato.cbf.service.PartidaService;
import br.com.aws.campeonato.cbf.service.TorneioService;
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
public class TorneioController extends BaseController implements TorneiosApi {

    @Autowired
    private TorneioService torneioService;

    @Autowired
    private PartidaService partidaService;

    @Autowired
    private TorneioMapper mapper;

    @Cacheable(value = "listaDeTorneios")
    @Override
    public ResponseEntity<ListaTorneiosResponseRepresentation> findAllTorneios() {
        List<Torneio> listaTorneios = torneioService.findAll();
        return createResponse(new ListaTorneiosResponseRepresentation()
                .torneios(mapper.toListTorneioRepresentation(listaTorneios))
        );
    }

    @Cacheable(value = "listaDeTorneios")
    @Override
    public ResponseEntity<TorneioResponseRepresentation> findTorneioById(Integer id) {
        Torneio torneio = torneioService.findById(id);
        return createResponse(mapper.toTorneioRepresentation(torneio));
    }

    @Caching(evict = {
            @CacheEvict(value = "listaDeTorneios", allEntries = true),
            @CacheEvict(value = "listaDePartidas", allEntries = true)
    })
    @Override
    public ResponseEntity<StandardSuccessRepresentation> insertTorneio(TorneioRequestRepresentation request) {
        torneioService.insert(Torneio.builder()
                .descricao(request.getDescricao())
                .build(), request.getIdsTimes()
        );
        return createResponseSuccess(HttpStatus.CREATED, "Torneio criado com sucesso.");
    }

    @Caching(evict = {
            @CacheEvict(value = "listaDeTorneios", allEntries = true),
            @CacheEvict(value = "listaDePartidas", allEntries = true)
    })
    @Override
    public ResponseEntity<StandardSuccessRepresentation> updateTorneio(Integer id, TorneioRequestRepresentation request) {
        torneioService.updateTorneio(id, request.getDescricao(), request.getIdsTimes());
        return createResponseSuccess(HttpStatus.OK, "Torneio atualizado com sucesso.");
    }

    @Caching(evict = {
            @CacheEvict(value = "listaDeTorneios", allEntries = true),
            @CacheEvict(value = "listaDePartidas", allEntries = true)
    })
    @Override
    public ResponseEntity<StandardSuccessRepresentation> deleteTorneioById(Integer id) {
        torneioService.deleteById(id);
        return createResponseSuccess(HttpStatus.OK, "Torneio excluído com sucesso.");
    }

    @CacheEvict(value = "listaDePartidas", allEntries = true)
    @Override
    public ResponseEntity<StandardSuccessRepresentation> iniciarPartida(Integer idTorneio, Integer idPartida) {
        partidaService.iniciarPartida(idTorneio, idPartida);
        return createResponseSuccess(HttpStatus.OK, "Partida iniciada com sucesso.");
    }

    @CacheEvict(value = "listaDePartidas", allEntries = true)
    @Override
    public ResponseEntity<StandardSuccessRepresentation> finalizarPartida(Integer idTorneio, Integer idPartida) {
        partidaService.finalizarPartida(idTorneio, idPartida);
        return createResponseSuccess(HttpStatus.OK, "Partida finalizada com sucesso.");
    }

    @CacheEvict(value = "listaDePartidas", allEntries = true)
    @Override
    public ResponseEntity<StandardSuccessRepresentation> marcarGol(Integer idTorneio, Integer idPartida, GolPartidaRequestRepresentation request) {
        partidaService.marcarGol(idTorneio, idPartida, request.getIdTime(), request.getIdJogador());
        return createResponseSuccess(HttpStatus.OK, "Gol marcado com sucesso.");
    }

    @CacheEvict(value = "listaDePartidas", allEntries = true)
    @Override
    public ResponseEntity<StandardSuccessRepresentation> marcarAcrescimo(Integer idTorneio, Integer idPartida, AcrescimoRequestRepresentation request) {
        partidaService.adicionarAcrescimos(idTorneio, idPartida, request.getValorAcrescimo());
        return createResponseSuccess(HttpStatus.OK, "Acréscimos marcado com sucesso.");
    }

    @CacheEvict(value = "listaDePartidas", allEntries = true)
    @Override
    public ResponseEntity<StandardSuccessRepresentation> marcarIntervalo(Integer idTorneio, Integer idPartida, IntervaloRequestRepresentation request) {
        partidaService.marcarIntervalo(idTorneio, idPartida, request.getValorIntervalo());
        return createResponseSuccess(HttpStatus.OK, "O intervalo foi marcado com sucesso.");
    }

    @CacheEvict(value = "listaDePartidas", allEntries = true)
    @Override
    public ResponseEntity<StandardSuccessRepresentation> advertirJogador(Integer idTorneio, Integer idPartida, AdvertenciaRequestRepresentation request) {
        partidaService.advertirJogador(idTorneio, idPartida, mapper.toAdvertenciaDomain(request));
        return createResponseSuccess(HttpStatus.OK, "A advertência foi realizada com sucesso.");
    }

    @CacheEvict(value = "listaDePartidas", allEntries = true)
    @Override
    public ResponseEntity<StandardSuccessRepresentation> substituirJogador(Integer idTorneio, Integer idPartida, SubstituicaoRequestRepresentation request) {
        partidaService.substituirJogador(idTorneio, idPartida, mapper.toSubstituicaoDomain(request));
        return createResponseSuccess(HttpStatus.OK, "O jogador foi substituído com sucesso.");
    }

}
