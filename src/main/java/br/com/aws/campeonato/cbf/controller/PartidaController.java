package br.com.aws.campeonato.cbf.controller;

import br.com.aws.campeonato.cbf.dto.PartidaDTO;
import br.com.aws.campeonato.cbf.mapper.PartidaMapper;
import br.com.aws.campeonato.cbf.provider.api.PartidasApi;
import br.com.aws.campeonato.cbf.provider.representation.ListaPartidasResponseRepresentation;
import br.com.aws.campeonato.cbf.provider.representation.PartidaRequestRepresentation;
import br.com.aws.campeonato.cbf.provider.representation.PartidaResponseRepresentation;
import br.com.aws.campeonato.cbf.provider.representation.StandardSuccessRepresentation;
import br.com.aws.campeonato.cbf.service.PartidaService;
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
public class PartidaController extends BaseController implements PartidasApi {

    @Autowired
    private PartidaService partidaService;

    @Autowired
    private PartidaMapper mapper;

    @Cacheable(value = "listaDePartidas")
    @Override
    public ResponseEntity<ListaPartidasResponseRepresentation> findAllPartidas() {
        List<PartidaDTO> listaPartidas = partidaService.findAll();
        return createResponse(new ListaPartidasResponseRepresentation()
                .partidas(mapper.toListPartidaRepresentation(listaPartidas))
        );
    }

    @Cacheable(value = "listaDePartidas")
    @Override
    public ResponseEntity<PartidaResponseRepresentation> findPartidaById(Integer id) {
        PartidaDTO partida = partidaService.findById(id);
        return createResponse(mapper.toPartidaRepresentation(partida));
    }

    @Caching(evict = {
            @CacheEvict(value = "listaDeJogadores", allEntries = true),
            @CacheEvict(value = "listaDePartidas", allEntries = true),
            @CacheEvict(value = "listaDeTimes", allEntries = true)
    })
    @Override
    public ResponseEntity<StandardSuccessRepresentation> insertPartida(PartidaRequestRepresentation request) {
        partidaService.criarPartida(request.getIdTorneio(), request.getIdTimeUm(), request.getIdTimeDois());
        return createResponseSuccess(HttpStatus.CREATED, "Partida criada com sucesso.");
    }

    @Caching(evict = {
            @CacheEvict(value = "listaDeJogadores", allEntries = true),
            @CacheEvict(value = "listaDePartidas", allEntries = true),
            @CacheEvict(value = "listaDeTimes", allEntries = true)
    })
    @Override
    public ResponseEntity<StandardSuccessRepresentation> deletePartidaById(Integer id) {
        partidaService.deleteById(id);
        return createResponseSuccess(HttpStatus.OK, "Partida excluída com sucesso.");
    }

}
