package br.com.aaas.bancohoras.repository;


import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

import br.com.aaas.bancohoras.domain.entity.Usuario;
import br.com.aaas.bancohoras.domain.entity.UsuarioPagina;
import br.com.aaas.bancohoras.infra.AbstractRepository;

public class UsuarioRepository extends AbstractRepository<Usuario, Long> {
  
  public Usuario consultarMatricula(String matricula) {
    Criteria crit = super.criarCriterio(Usuario.class);    
    crit.createAlias("setor", "se", JoinType.INNER_JOIN);
    crit.add(Restrictions.eq("matricula", matricula));
    return (Usuario) crit.uniqueResult();
  }
  
//  @Override
//  public Usuario alterar(Usuario entity) {
//    return super.alterar(entity);
//  }

  @SuppressWarnings("unchecked")
  public UsuarioPagina listar(String criterio, Integer tamanho, Integer pagina) {
    Criteria crit = this.montarCriteria(criterio);
    
    crit.addOrder(Order.asc("nome"));
    
    if (tamanho.compareTo(Integer.valueOf(0)) > 0 && pagina.compareTo(Integer.valueOf(0)) > 0) {
      crit.setMaxResults(tamanho);
      crit.setFirstResult((pagina - 1) * tamanho);      
    }    
    List<Usuario> usuarios = crit.list();
    
    Long total = this.contar(criterio);
    return new UsuarioPagina(usuarios, pagina, tamanho, total);
  }
  
  private Criteria montarCriteria(String criterio) {
    Criteria crit = super.criarCriterio(Usuario.class);
    crit.createAlias("setor", "se", JoinType.INNER_JOIN);
    
    if (criterio != null) {
      criterio = criterio.trim();
      if (this.isNumerico(criterio)) {
        crit.add(Restrictions.eq("matricula", criterio));
        
      } else {
        criterio = criterio.toUpperCase();
        crit.add(Restrictions.or(
            Restrictions.eq("se.sigla", criterio),
            Restrictions.ilike("nome", "%" + criterio + "%")
            ));
      }    
    }    
    return crit;
  }
  
  private Long contar(String criterio) {
    Criteria crit = this.montarCriteria(criterio);
    crit.setProjection(Projections.rowCount());
    return (Long) crit.uniqueResult();
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public Usuario altera(Usuario usuario, Usuario usuarioLogado) {
    return super.alterar(usuario, usuarioLogado);
  }

  public Usuario consultar(Long id) {
    return super.consultar(id, Usuario.class);
  }

  private boolean isNumerico(String criterio) {
    try {
      Long.valueOf(criterio);
      return true;
    } catch (Exception e) {
      return false;
    }    
  }
}
