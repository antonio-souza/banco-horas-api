package br.com.aaas.bancohoras.infra;

import javax.ws.rs.core.Response.Status;

public class RestException extends RuntimeException {
  
  private static final long serialVersionUID = 1L;
  
  private Status status;
  private ErroRestDTO erroRestDTO;

  public RestException(Status status, String message) {
    this.status = status;
    this.erroRestDTO = new ErroRestDTO(null, message, null, null);
  }

  public RestException(Status status, String id, String message, String field, String detail) {
    this.status = status;
    this.erroRestDTO = new ErroRestDTO(id, message, field, detail);
  }

  public Status getStatus() {
    return status;
  }
  
  public ErroRestDTO getErroRestED() {
    return erroRestDTO;
  }

}
