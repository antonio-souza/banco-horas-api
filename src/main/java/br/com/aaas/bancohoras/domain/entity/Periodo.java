package br.com.aaas.bancohoras.domain.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.aaas.bancohoras.infra.AbstractEntity;

@Entity
@Table(name = "bhr_periodo")
public class Periodo extends AbstractEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @SequenceGenerator(name = "Periodo_SEQ", sequenceName = "bhr_periodo_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Periodo_SEQ")
  @Column(name = "id_periodo")
  private Long              id;

  @Column(name = "dt_inicio")
  private LocalDate         dataInicio;

  @Override
  public Long getId() {
    return id;
  }

  public LocalDate getDataInicio() {
    return dataInicio;
  }

  public String getPeriodoFormatado() {
    return dataInicio.format(DateTimeFormatter.ofPattern("MM/yyyy"));
  }

  public String getDataInicioFormatada() {
    return dataInicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
  }

  @Override
  public String toString() {
    return getPeriodoFormatado();
  }
}
