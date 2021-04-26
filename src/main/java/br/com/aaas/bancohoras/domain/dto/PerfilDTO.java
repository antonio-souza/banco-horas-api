package br.com.aaas.bancohoras.domain.dto;

import br.com.aaas.bancohoras.domain.enums.PerfilEnum;

public class PerfilDTO {

  private Integer codigo;

  private String  nome;

  public PerfilDTO() {
    super();
  }

  public PerfilDTO(PerfilEnum perfilEnum) {
    super();
    this.codigo = perfilEnum.getCodigo();
    this.nome = perfilEnum.getNome();
  }

  public Integer getCodigo() {
    return codigo;
  }

  public String getNome() {
    return nome;
  }
}
