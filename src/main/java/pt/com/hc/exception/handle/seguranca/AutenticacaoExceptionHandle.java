package pt.com.hc.exception.handle.seguranca;

import javax.ws.rs.ext.Provider;

import pt.com.hc.exception.handle.BaseExceptionHandle;
import pt.com.hc.exception.seguranca.AutenticacaoException;

@Provider
public class AutenticacaoExceptionHandle extends BaseExceptionHandle<AutenticacaoException>{ }
