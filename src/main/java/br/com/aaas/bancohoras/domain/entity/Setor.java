package br.com.aaas.bancohoras.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.aaas.bancohoras.infra.AbstractEntity;

@Entity
@Table(name = "bhr_setor")
public class Setor extends AbstractEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @SequenceGenerator(name = "Setor_SEQ", sequenceName = "bhr_setor_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Setor_SEQ")
  @Column(name = "id_setor")
  private Long              id;

  @Column(name = "sigla")
  private String            sigla;

  @Override
  public Long getId() {
    return id;
  }

  public String getSigla() {
    return sigla;
  }
  
  @Override
  public String toString() {
    return sigla;
  }
}
