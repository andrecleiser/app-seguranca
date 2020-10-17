package pt.com.hc.util;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.NewCookie;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import pt.com.hc.dto.TokenDto;

@ApplicationScoped
public class CookieUtil {

    private static final String NOME_TOKEN_ACESSO = "tokenAcesso";
    private static final Integer EXPIRA_EM_MINUTOS = 60 * 5; // EXPIRA EM 5 MINUTOS

    @ConfigProperty(name = "conf.app.cookie.seguro")
    Boolean cookieSeguro;

    @ConfigProperty(name = "conf.app.cookie.dominio-api-recurso")
    String dominioApiRecurso;

    @ConfigProperty(name = "conf.app.cookie.dominio-api-seguranca")
    String dominioApiSeguranca;

    public NewCookie[] gerarCookieComTokenAcesso(TokenDto tokenAcesso) {
        List<NewCookie> listaCookies = new ArrayList<>();

        listaCookies.add(new NewCookie(NOME_TOKEN_ACESSO, tokenAcesso.getTokenAcesso(), "/", 
        // dominioApiRecurso, null, EXPIRA_EM_MINUTOS, false, false));
            ".herokuapp.com", null, EXPIRA_EM_MINUTOS, false, false));

        listaCookies.add(new NewCookie("refreshToken", tokenAcesso.getTokenAcesso(), "/",
            // dominioApiSeguranca, null, EXPIRA_EM_MINUTOS, cookieSeguro, true));
            ".herokuapp.com", null, EXPIRA_EM_MINUTOS, cookieSeguro, true));

        NewCookie[] cookies = new NewCookie[listaCookies.size()];
        cookies = listaCookies.toArray(cookies);

        return cookies;
    }

}
