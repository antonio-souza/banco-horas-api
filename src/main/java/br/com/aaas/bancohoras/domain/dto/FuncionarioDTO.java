package br.com.aaas.bancohoras.domain.dto;

import java.io.Serializable;

import br.com.aaas.bancohoras.domain.entity.Funcionario;

public class FuncionarioDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long              id;

  private String            matricula;

  private String            nome;

  private SetorDTO          setor;

  public FuncionarioDTO() {
    super();
  }

  public FuncionarioDTO(Funcionario funcionario) {
    super();
    this.id = funcionario.getId();
    this.matricula = funcionario.getMatricula();
    this.nome = funcionario.getNome();
    this.setor = new SetorDTO(funcionario.getSetor());
  }

  public Long getId() {
    return id;
  }

  public String getMatricula() {
    return matricula;
  }

  public String getNome() {
    return nome;
  }

  public SetorDTO getSetor() {
    return setor;
  }
}
