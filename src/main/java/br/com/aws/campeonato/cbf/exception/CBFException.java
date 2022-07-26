package br.com.aws.campeonato.cbf.exception;

public class CBFException extends RuntimeException {

    public CBFException() {
        super();
    }

    public CBFException(String msg){
        super(msg);
    }

    public CBFException(String msg, Throwable cause){
        super(msg, cause);
    }

}
