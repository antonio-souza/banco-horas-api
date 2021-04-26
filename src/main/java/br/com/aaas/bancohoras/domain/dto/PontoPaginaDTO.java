package br.com.aaas.bancohoras.domain.dto;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import br.com.aaas.bancohoras.domain.entity.PontoPagina;

public class PontoPaginaDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private List<PontoDTO>    pontos;

  private Integer           pagina;

  private Integer           tamanho;

  private Long              total;

  public PontoPaginaDTO() {
    super();
  }

  public PontoPaginaDTO(PontoPagina pontoPagina) {
    super();
    this.pontos = pontoPagina.getPontos().stream()
        .map(p -> new PontoDTO(p))
        .collect(Collectors.toList());
    this.pagina = pontoPagina.getPagina();
    this.tamanho = pontoPagina.getTamanho();
    this.total = pontoPagina.getTotal();
  }

  public List<PontoDTO> getPontos() {
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
