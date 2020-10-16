package pt.com.hc.exception.handle;

import java.util.Optional;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

import pt.com.hc.dto.ResponseErroDto;

public class BaseExceptionHandle<T extends Exception> implements ExceptionMapper<T> {

    @Override
    public Response toResponse(T exception) {
        Optional<Throwable> optionalCausa = Optional.ofNullable(exception.getCause());
        String mensagemCausa = optionalCausa.isPresent() ? optionalCausa.get().getMessage() : null;
        return Response
            .status(Status.BAD_REQUEST)
            .entity(new ResponseErroDto(exception.getMessage(), mensagemCausa))
            .build();
    }
    
}
