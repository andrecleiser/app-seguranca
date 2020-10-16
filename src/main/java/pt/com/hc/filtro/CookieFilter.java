package pt.com.hc.filtro;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.ext.Provider;

@Provider
public class CookieFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        for (String name : requestContext.getCookies().keySet()) {
            Cookie cookie = requestContext.getCookies().get(name);
            System.out.println("Segurança: " + cookie.getName());
        }
    }

}