package br.com.aaas.bancohoras.domain.dto;

import java.io.Serializable;

import br.com.aaas.bancohoras.domain.entity.Ponto;

public class PontoDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long              id;

  private PeriodoDTO        periodo;

  private FuncionarioDTO    funcionario;

  private String            saldoBanco;

  private String            saldoBancoAcumulado;

  private String            saldoGreve;

  private String            saldoGreveAcumulado;

  public PontoDTO() {
    super();
  }

  public PontoDTO(Ponto ponto) {
    super();
    this.id = ponto.getId();
    this.periodo = new PeriodoDTO(ponto.getPeriodo());
    this.funcionario = new FuncionarioDTO(ponto.getFuncionario());
    this.saldoBanco = ponto.getSaldoBancoFormatado();
    this.saldoBancoAcumulado = ponto.getSaldoBancoAcumuladoFormatado();
    this.saldoGreve = ponto.getSaldoGreveFormatado();
    this.saldoGreveAcumulado = ponto.getSaldoGreveAcumuladoFormatado();
  }

  public Long getId() {
    return id;
  }

  public PeriodoDTO getPeriodo() {
    return periodo;
  }

  public FuncionarioDTO getFuncionario() {
    return funcionario;
  }

  public String getSaldoBanco() {
    return saldoBanco;
  }

  public String getSaldoBancoAcumulado() {
    return saldoBancoAcumulado;
  }

  public String getSaldoGreve() {
    return saldoGreve;
  }

  public String getSaldoGreveAcumulado() {
    return saldoGreveAcumulado;
  }
}
