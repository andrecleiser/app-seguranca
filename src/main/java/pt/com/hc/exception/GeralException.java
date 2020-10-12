package pt.com.hc.exception;

public class GeralException extends RuntimeException {

    private static final long serialVersionUID = -5306300022565422786L;

    public GeralException(String mensagem) {
        super(mensagem);
    }
    
}