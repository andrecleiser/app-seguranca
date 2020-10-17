package pt.com.hc.filtro;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import pt.com.hc.util.CookieUtil;

@Provider
public class CookieFilter implements ContainerResponseFilter {

    // @Override
    // public void filter(ContainerRequestContext requestContext) throws IOException {
    //     for (String name : requestContext.getCookies().keySet()) {
    //         Cookie cookie = requestContext.getCookies().get(name);
    //         System.out.println("Segurança: " + cookie.getName());
    //     }
    // }

    @Inject
    CookieUtil cookieUtil;

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
            throws IOException {
        List<String> listaCookies = new ArrayList<String>();
        requestContext.getHeaders().entrySet().forEach(header -> System.out.println(header));
        responseContext
            .getHeaders()
            .entrySet()
            .stream()
            .filter(header -> header.getKey().equals("Set-Cookie"))
            .forEach(header -> header.getValue().forEach(token -> listaCookies.add(token + ";SameSite=None")));
        responseContext.getHeaders().remove("Set-Cookie");
        listaCookies.forEach(
            cookie -> responseContext.getHeaders().add("Set-Cookie", cookie));
    }

}