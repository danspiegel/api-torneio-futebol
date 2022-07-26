package br.com.aws.campeonato.cbf.exception;

import br.com.aws.campeonato.cbf.provider.representation.StandardErrorRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(CBFException.class)
    public ResponseEntity<StandardErrorRepresentation> handleDemoException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                createResponseError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Falha na requisição. ", e.getMessage())
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardErrorRepresentation> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                createResponseError(System.currentTimeMillis(), HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ocorreu um erro durante a operação. ", e.getMessage())
        );
    }

    private StandardErrorRepresentation createResponseError(Long timestamp, Integer status, String error, String message) {
        StandardErrorRepresentation err = new StandardErrorRepresentation();
        err.setTimestamp(timestamp);
        err.setStatus(status);
        err.setError(error);
        err.setMessage(message);
        return err;
    }

}
