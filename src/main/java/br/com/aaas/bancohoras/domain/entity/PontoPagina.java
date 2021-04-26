package br.com.aaas.bancohoras.domain.entity;

import java.util.List;

public class PontoPagina {

  private List<Ponto> pontos;

  private Integer     pagina;

  private Integer     tamanho;

  private Long        total;

  public PontoPagina(List<Ponto> pontos, Integer pagina, Integer tamanho, Long total) {
    super();
    this.pontos = pontos;
    this.pagina = pagina;
    this.tamanho = tamanho;
    this.total = total;
  }

  public List<Ponto> getPontos() {
    return pontos;
  }

  public Integer getPagina() {
    return pagina;
  }

  public Integer getTamanho() {
    return tamanho;
  }

  public Long getTotal() {
    return total;
  }
}
