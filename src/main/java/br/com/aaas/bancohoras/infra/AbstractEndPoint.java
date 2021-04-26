package br.com.aaas.bancohoras.infra;

import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractEndPoint {

  private Logger         logger = LoggerFactory.getLogger(this.getClass());

  protected Response tratarErro(Exception excecao) {

    if (excecao instanceof NaoAutenticadoException || excecao.getCause() != null && excecao.getCause() instanceof NaoAutenticadoException) {
      logger.warn(excecao.getMessage(), excecao.getCause());
      RestException restException = new RestException(Response.Status.PROXY_AUTHENTICATION_REQUIRED, excecao.getMessage());
      return Response.status(Response.Status.PROXY_AUTHENTICATION_REQUIRED).entity(restException).build();

    } else if (excecao instanceof NaoAutorizadoException || excecao.getCause() != null && excecao.getCause() instanceof NaoAutorizadoException) {
      logger.warn(excecao.getMessage(), excecao.getCause());
      return Response.status(Response.Status.UNAUTHORIZED).entity(excecao).build();

    } else if (excecao instanceof DomainException || excecao.getCause() != null && excecao.getCause() instanceof DomainException) {
      logger.warn(excecao.getMessage(), excecao.getCause());
      return Response.status(Response.Status.PRECONDITION_FAILED).entity(excecao).build();

    } else if (excecao instanceof IllegalArgumentException || excecao.getCause() != null && excecao.getCause() instanceof IllegalArgumentException) {
      logger.warn(excecao.getMessage(), excecao.getCause());
      return Response.status(Response.Status.BAD_REQUEST).entity(excecao).build();

    } else {
      logger.error(excecao.getMessage(), excecao);
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(excecao).build();
    }
  }
}
