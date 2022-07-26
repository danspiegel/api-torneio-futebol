package br.com.aws.campeonato.cbf.service;

import br.com.aws.campeonato.cbf.domain.*;
import br.com.aws.campeonato.cbf.dto.PartidaDTO;
import br.com.aws.campeonato.cbf.exception.CBFException;
import br.com.aws.campeonato.cbf.producer.ResultadoProducer;
import br.com.aws.campeonato.cbf.repository.AdvertenciaRepository;
import br.com.aws.campeonato.cbf.repository.GolRepository;
import br.com.aws.campeonato.cbf.repository.PartidaRepository;
import br.com.aws.campeonato.cbf.repository.SubstituicaoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PartidaService {

    @Autowired
    private PartidaRepository partidaRepository;

    @Autowired
    private GolRepository golRepository;

    @Autowired
    private SubstituicaoRepository substituicaoRepository;

    @Autowired
    private AdvertenciaRepository advertenciaRepository;

    @Autowired
    private TorneioService torneioService;

    @Autowired
    private TimeService timeService;

    @Autowired
    private JogadorService jogadorService;

    @Autowired
    private ResultadoProducer resultadoProducer;

    public void criarPartida(Integer idTorneio, Integer idTimeUm, Integer idTimeDois) {
        Torneio torneio = torneioService.findById(idTorneio);
        Time timeUm = timeService.findById(idTimeUm);
        Time timeDois = timeService.findById(idTimeDois);

        List<Integer> listaIdsTimes = torneio.getTimes().stream().map(Time::getId).collect(Collectors.toList());
        if (listaIdsTimes.stream().allMatch(id -> Arrays.asList(timeUm.getId(), timeDois.getId()).contains(id))) {
            partidaRepository.save(Partida.builder()
                    .timeUm(timeUm)
                    .timeDois(timeDois)
                    .torneio(torneio)
                    .build()
            );
        } else {
            throw new CBFException("Os times não estão no torneio.");
        }
    }

    public void marcarGol(Integer idTorneio, Integer idPartida, Integer idTime, Integer idJogador) {
        if (verificarPartidaOcorrendo(idTorneio, idPartida)) {
            Partida partida = buscarPartidaPorTorneio(idTorneio, idPartida);
            Time time = timeService.findById(idTime);
            Jogador jogador = jogadorService.buscarJogadorPorIdTime(idTime, idJogador);
            golRepository.save(Gol.builder()
                    .partida(partida)
                    .time(time)
                    .jogador(jogador)
                    .build()
            );
        } else {
            throw new CBFException("Esta partida não está ocorrendo.");
        }
    }

    public List<PartidaDTO> findAll() {
        return partidaRepository.findAll()
                .stream()
                .map(partida -> new PartidaDTO(partida,
                        calcularTotalGolsPorTimePartida(partida.getGols(), partida.getTimeUm().getId()),
                        calcularTotalGolsPorTimePartida(partida.getGols(), partida.getTimeDois().getId()))
                )
                .collect(Collectors.toList());
    }

    public PartidaDTO findById(Integer id) {
        Partida partida = partidaRepository.findById(id)
                .orElseThrow(() -> new CBFException("Partida não localizada"));
        return new PartidaDTO(partida,
                calcularTotalGolsPorTimePartida(partida.getGols(), partida.getTimeUm().getId()),
                calcularTotalGolsPorTimePartida(partida.getGols(), partida.getTimeDois().getId()));
    }

    public void deleteById(Integer id) {
        Partida partida = partidaRepository.findById(id)
                .orElseThrow(() -> new CBFException("Partida não localizada."));
        partidaRepository.delete(partida);
    }

    public Partida buscarPartidaPorTorneio(Integer idTorneio, Integer idPartida) {
        return partidaRepository.buscarPartidaPorTorneio(idTorneio, idPartida)
                .orElseThrow(() -> new CBFException("Partida não localizada no torneio."));
    }

    public void iniciarPartida(Integer idTorneio, Integer idPartida) {
        Partida partida = buscarPartidaPorTorneio(idTorneio, idPartida);

        if (!verificarPartidaIniciada(idTorneio, idPartida)) {
            partida.setDataHoraInicio(LocalDateTime.now());
            partidaRepository.save(partida);
        } else {
            throw new CBFException("A partida já foi iniciada.");
        }
    }

    public void finalizarPartida(Integer idTorneio, Integer idPartida) {
        Partida partida = buscarPartidaPorTorneio(idTorneio, idPartida);

        if (verificarPartidaIniciada(idTorneio, idPartida) && !verificarPartidaFinalizada(idTorneio, idPartida)) {
            partida.setDataHoraFim(LocalDateTime.now());
            partidaRepository.save(partida);
            enviarResultadoPartida(gerarPartidaResumida(idPartida));
        } else {
            throw new CBFException("A partida ainda não foi iniciada ou já foi finalizada.");
        }
    }

    private boolean verificarPartidaIniciada(Integer idTorneio, Integer idPartida) {
        Partida partida = buscarPartidaPorTorneio(idTorneio, idPartida);
        return Objects.nonNull(partida.getDataHoraInicio());
    }

    private boolean verificarPartidaFinalizada(Integer idTorneio, Integer idPartida) {
        Partida partida = buscarPartidaPorTorneio(idTorneio, idPartida);
        return Objects.nonNull(partida.getDataHoraFim());
    }

    public void adicionarAcrescimos(Integer idTorneio, Integer idPartida, Integer acrescimos) {

        if (verificarPartidaOcorrendo(idTorneio, idPartida)) {
            Partida partida = buscarPartidaPorTorneio(idTorneio, idPartida);

            if (Objects.isNull(partida.getAcrescimos())) {
                partida.setAcrescimos(0);
            }

            partida.setAcrescimos(partida.getAcrescimos() + acrescimos);
            partidaRepository.save(partida);
        } else {
            throw new CBFException("Esta partida não está ocorrendo.");
        }
    }

    public void marcarIntervalo(Integer idTorneio, Integer idPartida, Integer intervalo) {

        if (verificarPartidaOcorrendo(idTorneio, idPartida)) {
            Partida partida = buscarPartidaPorTorneio(idTorneio, idPartida);

            if (Objects.isNull(partida.getIntervalo())) {
                partida.setIntervalo(intervalo);
            } else {
                throw new CBFException("Esta partida já possui um intervalo marcado.");
            }

            partidaRepository.save(partida);
        } else {
            throw new CBFException("Esta partida não está ocorrendo.");
        }
    }

    private Long calcularTotalGolsPorTimePartida(List<Gol> listaGols, Integer idTime) {
        return listaGols.stream()
                .filter(gol -> gol.getTime().getId() == idTime)
                .count();
    }

    public void advertirJogador(Integer idTorneio, Integer idPartida, Advertencia advertencia) {

        if (verificarPartidaOcorrendo(idTorneio, idPartida)) {
            Partida partida = buscarPartidaPorTorneio(idTorneio, idPartida);
            Time time = timeService.findById(advertencia.getTime().getId());

            if (validarTimePartida(idPartida, time.getId())) {
                Jogador jogador = jogadorService.buscarJogadorPorIdTime(time.getId(), advertencia.getJogador().getId());
                advertenciaRepository.save(Advertencia.builder()
                        .partida(partida)
                        .time(time)
                        .jogador(jogador)
                        .tipoCartao(advertencia.getTipoCartao())
                        .build()
                );
            } else {
                throw new CBFException("Este time não está nessa partida.");
            }
        } else {
            throw new CBFException("Esta partida não está ocorrendo.");
        }
    }

    public void substituirJogador(Integer idTorneio, Integer idPartida, Substituicao substituicao) {

        if (verificarPartidaOcorrendo(idTorneio, idPartida)) {
            Partida partida = buscarPartidaPorTorneio(idTorneio, idPartida);
            Time time = timeService.findById(substituicao.getTime().getId());

            if (validarTimePartida(idPartida, time.getId())) {
                Jogador jogadorSubstituido = jogadorService.buscarJogadorPorIdTime(time.getId(), substituicao.getJogadorSubstituido().getId());
                Jogador jogadorSubstituto = jogadorService.buscarJogadorPorIdTime(time.getId(), substituicao.getJogadorSubstituto().getId());

                substituicaoRepository.save(Substituicao.builder()
                        .partida(partida)
                        .time(time)
                        .jogadorSubstituido(jogadorSubstituido)
                        .jogadorSubstituto(jogadorSubstituto)
                        .build()
                );
            } else {
                throw new CBFException("Este time não está nessa partida.");
            }
        } else {
            throw new CBFException("Esta partida não está ocorrendo.");
        }
    }

    private boolean validarTimePartida(Integer idPartida, Integer idTime) {
        return partidaRepository.buscarPartidaPorIdTime(idPartida, idTime)
                .isPresent();
    }

    private boolean verificarPartidaOcorrendo(Integer idTorneio, Integer idPartida) {
        return verificarPartidaIniciada(idTorneio, idPartida)
                && !verificarPartidaFinalizada(idTorneio, idPartida);
    }

    private void enviarResultadoPartida(PartidaDTO resultadoPartida) {
        resultadoProducer.enviarResultado(resultadoPartida);
    }

    private PartidaDTO gerarPartidaResumida(Integer idPartida) {
        PartidaDTO partida = findById(idPartida);
        PartidaDTO partidaResumida = new PartidaDTO();
        partidaResumida.setId(partida.getId());
        partidaResumida.setDataHoraInicio(partida.getDataHoraInicio());
        partidaResumida.setDataHoraFim(partida.getDataHoraFim());
        partidaResumida.setTimeUm(Time.builder().nome(partida.getTimeUm().getNome()).build());
        partidaResumida.setTimeDois(Time.builder().nome(partida.getTimeDois().getNome()).build());
        partidaResumida.setQtdGolsTimeUm(partida.getQtdGolsTimeUm());
        partidaResumida.setQtdGolsTimeDois(partida.getQtdGolsTimeDois());
        return partidaResumida;
    }

}
