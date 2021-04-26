package br.com.aaas.bancohoras.domain.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.aaas.bancohoras.infra.AbstractEntity;

@Entity
@Table(name = "bhr_funcionario")
public class Funcionario extends AbstractEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @SequenceGenerator(name = "Funcionario_SEQ", sequenceName = "bhr_funcionario_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Funcionario_SEQ")
  @Column(name = "id_funcionario")
  private Long              id;

  @Column(name = "matricula")
  private String            matricula;

  @Column(name = "nome")
  private String            nome;

  @JoinColumn(name = "id_setor")
  @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
  private Setor             setor;

  @Column(name = "senha")
  private String            senha;

  @Override
  public Long getId() {
    return id;
  }

  public String getMatricula() {
    return matricula;
  }

  public String getNome() {
    return nome;
  }

  public Setor getSetor() {
    return setor;
  }
  
  @Override
  public String toString() {
    return String.format("%s - %s - %s", matricula, nome, setor);
  }
}
