package br.com.aws.campeonato.cbf.producer;

import br.com.aws.campeonato.cbf.dto.PartidaDTO;
import br.com.aws.campeonato.cbf.exception.CBFException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ResultadoProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final String ROUTING_KEY_EXCHANGE = "events.business.cbfchampion";
    private static final String ROUTING_KEY_RESULTADO = "resultado.partida.v1";

    public void enviarResultado(PartidaDTO resultadoPartida) {
        rabbitTemplate.convertAndSend(ROUTING_KEY_EXCHANGE, ROUTING_KEY_RESULTADO, new Gson().toJson(resultadoPartida));
        log.info("Mensagem publicada na fila de resultado {}.", ROUTING_KEY_RESULTADO);
    }

}
