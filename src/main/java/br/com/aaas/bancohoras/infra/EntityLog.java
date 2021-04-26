package br.com.aaas.bancohoras.infra;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.Formula;

@Embeddable
public class EntityLog {

  @Column(name = "log_dh_inclusao")
  private LocalDateTime dhInclusao;

  @Column(name = "log_dh_alteracao")
  private LocalDateTime dhAlteracao;

  @Column(name = "log_user_inclusao")
  private Long          userInclusao;

  @Column(name = "log_user_alteracao")
  private Long          userAlteracao;

  @Column(name = "log_ip_alteracao")
  private String        ipInclusao;

  @Column(name = "log_ip_inclusao")
  private String        ipAlteracao;

  @Formula("1")
  private int           impedeEmbeddableNulo;

  public LocalDateTime getDhInclusao() {
    return dhInclusao;
  }

  public void setDhInclusao(LocalDateTime dhInclusao) {
    this.dhInclusao = dhInclusao;
  }

  public LocalDateTime getDhAlteracao() {
    return dhAlteracao;
  }

  public void setDhAlteracao(LocalDateTime dhAlteracao) {
    this.dhAlteracao = dhAlteracao;
  }

  public Long getUserInclusao() {
    return userInclusao;
  }

  public void setUserInclusao(Long userInclusao) {
    this.userInclusao = userInclusao;
  }

  public Long getUserAlteracao() {
    return userAlteracao;
  }

  public void setUserAlteracao(Long userAlteracao) {
    this.userAlteracao = userAlteracao;
  }

  public String getIpInclusao() {
    return ipInclusao;
  }

  public void setIpInclusao(String ipInclusao) {
    this.ipInclusao = ipInclusao;
  }

  public String getIpAlteracao() {
    return ipAlteracao;
  }

  public void setIpAlteracao(String ipAlteracao) {
    this.ipAlteracao = ipAlteracao;
  }
}
