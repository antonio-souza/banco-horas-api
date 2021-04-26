package br.com.aaas.bancohoras.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

import br.com.aaas.bancohoras.domain.entity.Funcionario;
import br.com.aaas.bancohoras.infra.AbstractRepository;

public class FuncionarioRepository extends AbstractRepository<Funcionario, Long> {

  public Funcionario consultarMatricula(String matricula) {
    Criteria crit = super.criarCriterio(Funcionario.class);
    crit.add(Restrictions.eq("matricula", matricula));
    return (Funcionario) crit.uniqueResult();
  }

  @SuppressWarnings("unchecked")
  public List<Funcionario> listar(Long idSetor) {
    Criteria crit = super.criarCriterio(Funcionario.class);
    crit.createAlias("setor", "se", JoinType.INNER_JOIN);
    crit.add(Restrictions.eq("se.id", idSetor));
    crit.addOrder(Order.asc("nome"));
    return crit.list();
  }

  @SuppressWarnings("unchecked")
  public List<Funcionario> listar(String criterio) {
    Criteria crit = super.criarCriterio(Funcionario.class);
    crit.createAlias("setor", "se", JoinType.INNER_JOIN);
    
    if (criterio != null) {
      criterio = criterio.trim();
      if (this.isNumerico(criterio)) {
        crit.add(Restrictions.eq("matricula", criterio));
        
      } else {
        crit.add(Restrictions.or(
            Restrictions.eq("se.sigla", criterio),
            Restrictions.ilike("nome", "%" + criterio + "%")
            ));
      }    
    }    
    crit.addOrder(Order.asc("nome"));
    return crit.list();
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
