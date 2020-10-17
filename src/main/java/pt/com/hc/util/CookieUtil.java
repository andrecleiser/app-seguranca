package pt.com.hc.util;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.NewCookie;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import pt.com.hc.dto.TokenDto;

@ApplicationScoped
public class CookieUtil {

    private static final String NOME_REFRESH_TOKEN = "refreshToken";
    // private static final String NOME_TOKEN_ACESSO = "tokenAcesso";
    private static final Integer TEMPO_VIDA_COOKIE = 60 * 5;

    @ConfigProperty(name = "conf.app.cookie.seguro")
    Boolean cookieSeguro;

    public NewCookie[] gerarCookieComTokenAcesso(TokenDto tokenAcesso) {
        List<NewCookie> listaCookies = new ArrayList<>();
        String dominio = cookieSeguro ? "app-seguranca-api.herokuapp.com" : null;
        listaCookies.add(new NewCookie(NOME_REFRESH_TOKEN, tokenAcesso.getTokenAcesso(), "/", 
            dominio, null, TEMPO_VIDA_COOKIE, cookieSeguro, false));

        // listaCookies.add(new NewCookie("refreshToken", tokenAcesso.getTokenAcesso(), "/",
        //     null, null, EXPIRA_EM_MINUTOS, cookieSeguro, true));

        NewCookie[] cookies = new NewCookie[listaCookies.size()];
        cookies = listaCookies.toArray(cookies);

        return cookies;
    }

}
