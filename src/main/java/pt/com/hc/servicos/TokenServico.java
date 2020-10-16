package pt.com.hc.servicos;

import java.security.PrivateKey;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;
import pt.com.hc.dto.TokenDto;
import pt.com.hc.entidade.Usuario;

/**
 * Utilities for generating a JWT for testing
 */
@ApplicationScoped
public class TokenServico {

    @Inject
    AutenticarServico autenticarServico;

    /**
     * Utility method to generate a JWT string from a JSON resource file that is
     * signed by the privateKey.pem test resource key, possibly with invalid fields.
     *
     * @param jsonUsuarioAutenticado - name of test resources file
     * @return the JWT string
     * @throws Exception on parse failure
     */
    public TokenDto gerarToken(Usuario usuario, PrivateKey chave) {
        JwtClaimsBuilder tokenAcesso = Jwt.claims();
    
        long currentTimeInSecs = currentTimeInSecs();
        // Experia em 5 minutos
        long exp = currentTimeInSecs + (60 * 1);
    
        tokenAcesso.issuedAt(currentTimeInSecs)
            .upn(usuario.email)
            .preferredUserName(usuario.nome)
            .expiresAt(exp)
            .issuer("app-security")
            .claim("idUsuario", usuario.idUsuario)
            .groups(usuario.getPerfis().stream().map(perfil -> perfil.rule).collect(Collectors.toSet()));

        return new TokenDto(tokenAcesso.jws().sign(chave));
    }

    /**
     * @return the current time in seconds since epoch
     */
    private int currentTimeInSecs() {
        long currentTimeMS = System.currentTimeMillis();
        return (int) (currentTimeMS / 1000);
    }
}