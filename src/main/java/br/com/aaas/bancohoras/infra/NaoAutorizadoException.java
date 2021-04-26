package br.com.aaas.bancohoras.infra;

public class NaoAutorizadoException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public NaoAutorizadoException(String mensagem) {
		super(mensagem);
	}
}
