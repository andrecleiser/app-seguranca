package pt.com.hc.exception.seguranca;

public class AutenticacaoException extends Exception {

    private static final long serialVersionUID = 6985799963601860185L;

    public AutenticacaoException() {
        super("Falha na autenticação. Usuário ou senha inválido!");
    }
}
