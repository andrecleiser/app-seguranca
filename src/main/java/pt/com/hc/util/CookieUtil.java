package pt.com.hc.util;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.NewCookie;

import pt.com.hc.dto.TokenDto;

public class CookieUtil {

    private static final String NOME_TOKEN_ACESSO = "tokenAcesso";
    private static final Integer EXPIRA_EM_MINUTOS = 60 * 5; // EXPIRA EM 5 MINUTOS

    public static NewCookie[] gerarCookieComTokenAcesso(TokenDto tokenAcesso) {
        List<NewCookie> listaCookies = new ArrayList<>();

        listaCookies.add(new NewCookie(NOME_TOKEN_ACESSO, tokenAcesso.getTokenAcesso(), "/", "api-gestao-associados.herokuapp.com", null,
                EXPIRA_EM_MINUTOS, false, false));

        // listaCookies.add(new NewCookie("tokenAcesso", tokenAcesso.getTokenAcesso(), "/", null, null,
        //         EXPIRA_EM_MINUTOS, false, false));

        NewCookie[] cookies = new NewCookie[listaCookies.size()];
        cookies = listaCookies.toArray(cookies);

        return cookies;
    }

}
