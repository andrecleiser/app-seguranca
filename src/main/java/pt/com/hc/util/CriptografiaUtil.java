package pt.com.hc.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import pt.com.hc.enums.TipoAlgoritmoCriptografiaEnum;
import pt.com.hc.exception.GeralException;

/**
 * Classe utilitária que provê mecanismos de segurança
 */
public class CriptografiaUtil {
    
    /**
     * Construtor privado.
     */
    private CriptografiaUtil() {}

	/**
     * @param valor - valor utilizado para gerar o hash.
     * @return hash do valor + frase passados com algoritmo SHA.
     */
    public static String gerarHash(String valor) {
        return CriptografiaUtil.gerarHash(valor, null, TipoAlgoritmoCriptografiaEnum.SHA);
    }

	/**
     * @param valor - valor utilizado para gerar o hash.
     * @param frase - frase que será combinada com o valor.
     * @return hash do valor + frase passados com algoritmo SHA.
     */
    public static String gerarHash(String valor, String frase) {
        return CriptografiaUtil.gerarHash(valor, frase, TipoAlgoritmoCriptografiaEnum.SHA);
    }
	
	/**
     * @param valor - valor utilizado para gerar o hash.
     * @param algoritmo - algorimo que será utilizado para gerar o hash
     * @return hash do valor com algoritmo informado.
     */
    public static String gerarHash(String valor, TipoAlgoritmoCriptografiaEnum algoritmo) {
        return CriptografiaUtil.gerarHash(valor, null, algoritmo);
    }

	/**
     * Gera o hash de um texto.
     * 
     * @param texto - texto a ser cifrado
     * @param frase - frase que será combinada com o texto (opcional)
     * @param algoritmo - algorimo utilizado para gerar o hash do texto
     * @return hash do texto + frase passados
     */
    public static String gerarHash(String texto, String frase, TipoAlgoritmoCriptografiaEnum algoritmo) {
    
        try {
            final MessageDigest md = MessageDigest.getInstance(algoritmo.getTipoAlgoritmo());
            final Charset utf8 = StandardCharsets.UTF_8;
            
            if (frase != null) {
                md.update(frase.getBytes(utf8));
                final byte[] salt = md.digest();
        
                md.reset();
                md.update(texto.getBytes(utf8));
                md.update(salt);
            } else {
                md.update(texto.getBytes(utf8));
        	}
    
            final byte[] raw = md.digest();
            return new String(Base64.getEncoder().encode(raw), utf8);
    		
    	} catch (final NoSuchAlgorithmException e) {
            throw new GeralException("O algoritmo de criptografia informado não foi encontrado.");
        }
    }   
}