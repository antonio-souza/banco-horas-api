package br.com.aaas.bancohoras.infra;

import java.time.LocalDateTime;

import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import br.com.aaas.bancohoras.domain.entity.Usuario;

public class EntityListener {
  
  @PostLoad
  public void postLoad(AbstractEntity entity) {
    entity.setHashConstrucao(entity.hashCode());
  }

  @PrePersist
  public void prePersist(AbstractEntity entity) {
    Usuario usuario = entity.getUsuarioLogado();
    if (usuario == null) {
      return;
    }
    entity.getLog().setDhInclusao(LocalDateTime.now());
    entity.getLog().setUserInclusao(Long.valueOf(usuario.getMatricula()));
    entity.getLog().setIpInclusao(usuario.getIp());
  }

  @PreUpdate
  public void preUpdate(AbstractEntity entity) { 
    Usuario usuario = entity.getUsuarioLogado();
    if (usuario == null || entity.getHashConstrucao() == entity.hashCode()) {
      return;
    }
    entity.getLog().setDhAlteracao(LocalDateTime.now());
    entity.getLog().setUserAlteracao(Long.valueOf(usuario.getMatricula()));
    entity.getLog().setIpAlteracao(usuario .getIp());
  }
}
