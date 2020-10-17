package pt.com.hc.api;

import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.resteasy.spi.HttpRequest;

import pt.com.hc.dto.SegurancaDto;
import pt.com.hc.dto.TokenDto;
import pt.com.hc.dto.UsuarioDto;
import pt.com.hc.entidade.Usuario;
import pt.com.hc.exception.GeralException;
import pt.com.hc.exception.seguranca.AutenticacaoException;
import pt.com.hc.servicos.AutenticarServico;
import pt.com.hc.servicos.TokenServico;
import pt.com.hc.util.CookieUtil;
import pt.com.hc.util.CriptografiaUtil;
import pt.com.hc.util.EncryptDecrypt;

@Path("/autenticacao")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AutenticacaoApi {

    @Inject
    JsonWebToken jwt;

    @Inject
    TokenServico tokenServico;

    @Inject
    AutenticarServico autenticacaoServico;

    @Inject
    EncryptDecrypt ed;

    @Inject
    CookieUtil cookieUtil;

    @POST
    @Path("senha")
    public Response obterSenhaCriptografada(SegurancaDto credenciais) throws GeralException {
        String hashSenha = CriptografiaUtil.gerarHash(credenciais.getHashSenha());
        String chaveEnc = this.ed.encrypt(credenciais.getChavePrivadaEncripty());

        return Response.ok(new SegurancaDto(hashSenha, chaveEnc)).build();
    }

    @POST
    public Response autenticar(UsuarioDto autenticacao, @Context HttpRequest httpRequest) throws GeralException,
            NoSuchAlgorithmException, InvalidKeySpecException, AutenticacaoException {
        PrivateKey chave = null;
        Usuario usuario = null;

        Optional<String> appCliente = Optional
                .ofNullable(httpRequest.getHttpHeaders().getHeaderString("Authorization"));

        if (!appCliente.isPresent()) {
            throw new GeralException("Credencias de sistema não informadas da forma correta.");
        }

        chave = this.autenticacaoServico.obterChavePrivadaAplicacao(appCliente.get());
        usuario = this.autenticacaoServico.validarUsuario(autenticacao.getUsuario(), autenticacao.getSenha());

        TokenDto token = this.tokenServico.gerarToken(usuario, chave);
        return Response
            .status(Status.CREATED)
            .cookie(this.cookieUtil.gerarCookieComTokenAcesso(token))
            .entity(token)
            .build();
    }
}