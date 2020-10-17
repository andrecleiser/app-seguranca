package pt.com.hc.filtro;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@Provider
public class CookieResponseFilter implements ContainerResponseFilter {

    @ConfigProperty(name = "conf.app.cookie.seguro")
    Boolean cookieSeguro;

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
            throws IOException {
        List<String> listaCookies = new ArrayList<String>();
        String indicaSameSite = cookieSeguro ? ";SameSite=None" : ";SameSite=Lax";
        
        responseContext
            .getHeaders()
            .entrySet()
            .stream()
            .filter(header -> header.getKey().equals("Set-Cookie"))
            .forEach(header -> header.getValue().forEach(token -> listaCookies.add(token + indicaSameSite)));
        responseContext.getHeaders().remove("Set-Cookie");
        listaCookies.forEach(
            cookie -> responseContext.getHeaders().add("Set-Cookie", cookie));
    }

}