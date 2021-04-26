package br.com.aaas.bancohoras.infra;

public class NaoAutenticadoException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public NaoAutenticadoException(String mensagem) {
		super(mensagem);
	}
}
