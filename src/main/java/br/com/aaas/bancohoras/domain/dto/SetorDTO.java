package br.com.aaas.bancohoras.domain.dto;

import java.io.Serializable;

import br.com.aaas.bancohoras.domain.entity.Setor;

public class SetorDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long              id;

  private String            sigla;

  public SetorDTO() {
    super();
  }

  public SetorDTO(Setor setor) {
    super();
    this.id = setor.getId();
    this.sigla = setor.getSigla();
  }

  public Long getId() {
    return id;
  }

  public String getSigla() {
    return sigla;
  }
}
