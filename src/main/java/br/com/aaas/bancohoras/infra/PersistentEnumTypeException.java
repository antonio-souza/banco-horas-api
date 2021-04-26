package br.com.aaas.bancohoras.infra;


public class PersistentEnumTypeException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public PersistentEnumTypeException(String message, Throwable cause) {
    super(message, cause);
  }
}
