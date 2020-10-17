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

    @ConfigProperty(name = "conf.app.cookie.seguro")
    Boolean cookieSeguro;

    @ConfigProperty(name = "conf.app.cookie.tempo-vida")
    Integer tempoVida;

    public NewCookie[] gerarCookieComTokenAcesso(TokenDto tokenAcesso) {
        List<NewCookie> listaCookies = new ArrayList<>();
        String dominio = cookieSeguro ? "app-gestao-associado-frontend.herokuapp.com" : null;
        listaCookies.add(new NewCookie(NOME_TOKEN_ACESSO, tokenAcesso.getTokenAcesso(), "/", 
            dominio, null, tempoVida, cookieSeguro, false));

        // listaCookies.add(new NewCookie("refreshToken", tokenAcesso.getTokenAcesso(), "/",
        //     null, null, EXPIRA_EM_MINUTOS, cookieSeguro, true));

        NewCookie[] cookies = new NewCookie[listaCookies.size()];
        cookies = listaCookies.toArray(cookies);

        return cookies;
    }

}
