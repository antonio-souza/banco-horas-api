package br.com.aaas.bancohoras.domain.entity;

import java.util.List;

public class UsuarioPagina {

  private List<Usuario> usuarios;

  private Integer       pagina;

  private Integer       tamanho;

  private Long          total;

  public UsuarioPagina(List<Usuario> pontos, Integer pagina, Integer tamanho, Long total) {
    super();
    this.usuarios = pontos;
    this.pagina = pagina;
    this.tamanho = tamanho;
    this.total = total;
  }

  public List<Usuario> getUsuarios() {
    return usuarios;
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
