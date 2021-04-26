package br.com.aaas.bancohoras.domain.enums;

import br.com.aaas.bancohoras.infra.PersistentEnum;

public enum PerfilEnum implements PersistentEnum<Integer> {
  ADMINISTRADOR(1, "Admistrador"), 
  GERENTE(2, "Gerente"), 
  CHEFFE(3, "Chefe"), 
  FUNCIONARIO(4, "Funcionário");
  
  private Integer codigo;
  
  private String nome;

  private PerfilEnum(Integer codigo, String nome) {
    this.codigo = codigo;
    this.nome = nome;
  }

  public Integer getCodigo() {
    return codigo;
  }

  public String getNome() {
    return nome;
  }

  @Override
  public Integer getValorQueDeveSerPersistido() {
    return codigo;
  }  
}
