package br.com.aws.campeonato.cbf.controller;

import br.com.aws.campeonato.cbf.domain.Jogador;
import br.com.aws.campeonato.cbf.mapper.JogadorMapper;
import br.com.aws.campeonato.cbf.provider.api.JogadoresApi;
import br.com.aws.campeonato.cbf.provider.representation.*;
import br.com.aws.campeonato.cbf.service.JogadorService;
import br.com.aws.campeonato.cbf.service.TransferenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/cbf")
public class JogadorController extends BaseController implements JogadoresApi {

    @Autowired
    private JogadorService jogadorService;

    @Autowired
    private TransferenciaService transferenciaService;

    @Autowired
    private JogadorMapper mapper;

    @Cacheable(value = "listaDeJogadores")
    @Override
    public ResponseEntity<ListaJogadoresResponseRepresentation> findAllJogadores() {
        List<Jogador> listaJogadores = jogadorService.findAll();
        return createResponse(new ListaJogadoresResponseRepresentation()
                .jogadores(mapper.toListJogadorRepresentation(listaJogadores))
        );
    }

    @Cacheable(value = "listaDeJogadores")
    @Override
    public ResponseEntity<JogadorResponseRepresentation> findJogadorById(Integer id) {
        Jogador jogador = jogadorService.findById(id);
        return createResponse(mapper.toJogadorRepresentation(jogador));
    }

    @CacheEvict(value = "listaDeJogadores", allEntries = true)
    @Override
    public ResponseEntity<StandardSuccessRepresentation> deleteJogadorById(Integer id) {
        jogadorService.deleteById(id);
        return createResponseSuccess(HttpStatus.OK, "Jogador excluído com sucesso.");
    }

    @CacheEvict(value = "listaDeJogadores", allEntries = true)
    @Override
    public ResponseEntity<StandardSuccessRepresentation> insertJogador(JogadorRequestRepresentation request) {
        jogadorService.insert(mapper.toJogadorDomain(request));
        return createResponseSuccess(HttpStatus.OK, "Jogador inserido com sucesso.");
    }

    @CacheEvict(value = "listaDeJogadores", allEntries = true)
    @Override
    public ResponseEntity<StandardSuccessRepresentation> updateJogador(Integer id, JogadorRequestRepresentation request) {
        jogadorService.update(id, mapper.toJogadorDomain(request));
        return createResponseSuccess(HttpStatus.OK, "Jogador atualizado com sucesso.");
    }

    @CacheEvict(value = "listaDeJogadores", allEntries = true)
    @Override
    public ResponseEntity<StandardSuccessRepresentation> transferirJogador(TransferenciaRepresentation request) {
        transferenciaService.updateTime(request.getIdJogador(), request.getIdTime());
        return createResponseSuccess(HttpStatus.OK, "Transferência do jogador realizada com sucesso.");
    }

}
