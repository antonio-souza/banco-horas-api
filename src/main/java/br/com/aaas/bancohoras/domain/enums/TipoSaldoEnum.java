package br.com.aaas.bancohoras.domain.enums;

import br.com.aaas.bancohoras.infra.PersistentEnum;

public enum TipoSaldoEnum implements PersistentEnum<Integer> {
  DATA_COMEMORATIVA(1, "Data Comemorativa"), 
  BANCO_HORAS(2, "Banco de Horas"), 
  GREVE(3, "Greve");
  
  private Integer codigo;
  
  private String nome;

  private TipoSaldoEnum(Integer codigo, String nome) {
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
