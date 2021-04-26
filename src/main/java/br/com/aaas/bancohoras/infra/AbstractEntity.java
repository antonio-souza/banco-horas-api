package br.com.aaas.bancohoras.infra;

import javax.persistence.Embedded;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import br.com.aaas.bancohoras.domain.entity.Usuario;

@MappedSuperclass
@EntityListeners(value = EntityListener.class)
public abstract class AbstractEntity {

  @Embedded
  private EntityLog log;

  @Transient
  private Boolean   removida;

  @Transient
  private Usuario   usuarioLogado;

  @Transient
  private int       hashConstrucao;

  public AbstractEntity() {
    super();
    this.removida = false;
    this.log = new EntityLog();
  }

  public EntityLog getLog() {
    return log;
  }

  public void setLog(EntityLog log) {
    this.log = log;
  }

  public boolean isRemovida() {
    return removida;
  }

  public void remover() {
    removida = true;
  }

  public abstract Object getId();

  public Boolean getRemovida() {
    return removida;
  }

  public void setRemovida(Boolean removida) {
    this.removida = removida;
  }

  public void setUsuarioLogado(Usuario usuarioLogado) {
    this.usuarioLogado = usuarioLogado;
  }

  public Usuario getUsuarioLogado() {
    return usuarioLogado;
  }

  public int getHashConstrucao() {
    return hashConstrucao;
  }

  public void setHashConstrucao(int hashConstrucao) {
    this.hashConstrucao = hashConstrucao;
  }

  @SuppressWarnings("rawtypes")
  public Class getSuperClasseMapeada() {
    return null;
  }
}
