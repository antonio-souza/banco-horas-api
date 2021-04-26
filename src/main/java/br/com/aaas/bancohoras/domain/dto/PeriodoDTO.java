package br.com.aaas.bancohoras.domain.dto;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;

import br.com.aaas.bancohoras.domain.entity.Periodo;

public class PeriodoDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private String         dataInicio;

  public PeriodoDTO() {
    super();
  }

  public PeriodoDTO(Periodo periodo) {
    super();
    this.dataInicio = periodo.getDataInicio().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
  }

  public String getDataInicio() {
    return dataInicio;
  }
}
