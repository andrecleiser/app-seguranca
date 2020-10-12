package pt.com.hc.servicos;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import pt.com.hc.entidade.Sistema;
import pt.com.hc.entidade.Usuario;
import pt.com.hc.exception.GeralException;
import pt.com.hc.util.CriptografiaUtil;
import pt.com.hc.util.EncryptDecrypt;

@ApplicationScoped
public class AutenticarServico {

    @Inject
    EncryptDecrypt encryptDecrypt;

    public PrivateKey obterChavePrivadaAplicacao(String appCliente)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        Optional<Sistema> sistema = this.validarCredencialApplicacao(appCliente);

        if (!sistema.isPresent()) {
            throw new GeralException("Identificação do sistema inválida!");
        }

        String chave = this.encryptDecrypt.decrypt(sistema.get().chave_privada);
        byte[] encodedBytes = toEncodedBytes(chave);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(keySpec);
    }

    private Optional<Sistema> validarCredencialApplicacao(String appCliente) {
        String credenciaisDecode = new String(Base64.getDecoder().decode(appCliente.replace("Basic ", "")));
        String[] credenciais = credenciaisDecode.split(":");

        Optional<Sistema> sistema = Optional
            .ofNullable(
                Sistema.find("clienteId = ?1 and clienteSecret = ?2", credenciais[0], credenciais[1]).firstResult()
            );

        return sistema;
    }

    private static byte[] toEncodedBytes(final String pemEncoded) {
        final String normalizedPem = pemEncoded;
        return Base64.getDecoder().decode(normalizedPem);
    }

    public Usuario validarUsuario(String email, String senha) {
        Optional<Usuario> usuario = Optional.ofNullable(Usuario.find("email", email).firstResult());

        if (usuario.isPresent() && (!usuario.get().senha.equals(CriptografiaUtil.gerarHash(senha)))) {
            throw new GeralException("Usuário ou senha inválido!");
        }

        return usuario.get();
    }
}