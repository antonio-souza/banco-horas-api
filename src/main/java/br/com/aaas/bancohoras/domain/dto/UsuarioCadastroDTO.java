package br.com.aaas.bancohoras.domain.dto;

import java.io.Serializable;

public class UsuarioCadastroDTO implements Serializable {

  private static final long serialVersionUID = 1L;
  
  private String matricula;

  private String senhaAtual;

  private String senhaNova;

  private String senhaNovaConfirmacao;
  
  private String ip;

  public String getMatricula() {
    return matricula;
  }

  public void setMatricula(String matricula) {
    this.matricula = matricula;
  }

  public String getSenhaAtual() {
    return senhaAtual;
  }

  public void setSenhaAtual(String senhaAtual) {
    this.senhaAtual = senhaAtual;
  }

  public String getSenhaNova() {
    return senhaNova;
  }

  public void setSenhaNova(String senhaNova) {
    this.senhaNova = senhaNova;
  }

  public String getSenhaNovaConfirmacao() {
    return senhaNovaConfirmacao;
  }

  public void setSenhaNovaConfirmacao(String senhaNovaConfirmacao) {
    this.senhaNovaConfirmacao = senhaNovaConfirmacao;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }
}
