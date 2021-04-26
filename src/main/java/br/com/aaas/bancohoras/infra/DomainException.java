package br.com.aaas.bancohoras.infra;

public class DomainException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public DomainException(String mensagem) {
		super(mensagem);
	}
}
