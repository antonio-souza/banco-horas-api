package br.com.aaas.bancohoras.domain.dto;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import br.com.aaas.bancohoras.domain.entity.UsuarioPagina;

public class UsuarioPaginaDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private List<UsuarioDTO>  usuarios;

  private Integer           pagina;

  private Integer           tamanho;

  private Long              total;

  public UsuarioPaginaDTO() {
    super();
  }

  public UsuarioPaginaDTO(UsuarioPagina usuarioPagina) {
    super();
    this.usuarios = usuarioPagina.getUsuarios().stream()
        .map(u -> new UsuarioDTO(u))
        .collect(Collectors.toList());
    this.pagina = usuarioPagina.getPagina();
    this.tamanho = usuarioPagina.getTamanho();
    this.total = usuarioPagina.getTotal();
  }

  public List<UsuarioDTO> getUsuarios() {
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
