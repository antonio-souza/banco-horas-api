package br.com.aaas.bancohoras.infra;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import br.com.aaas.bancohoras.domain.entity.Usuario;

public abstract class AbstractRepository<E extends AbstractEntity, I> {

  @PersistenceContext(unitName = "BHR")
  private EntityManager entityManager;

  protected EntityManager getEntityManager() {
    return entityManager;
  }

  protected Session getSession() {
    return entityManager.unwrap(Session.class);
  }

  public E incluir(E entity, Usuario usuario) {
    EntityUtil.listarEntidadesEmCascata(entity).stream()
      .forEach(e -> e.setUsuarioLogado(usuario));
    
    this.getSession().persist(entity);
    this.getSession().flush();
    return entity;
  }

  protected E alterar(E entity, Usuario usuario) {    
    Map<Boolean, List<AbstractEntity>> mapaEntidadesRemovidas = EntityUtil.listarEntidadesEmCascata(entity).stream()
        .collect(Collectors.partitioningBy(e -> e.isRemovida()));
    
    mapaEntidadesRemovidas.get(false).stream()
      .forEach(e -> e.setUsuarioLogado(usuario));
    
    this.getSession().saveOrUpdate(entity);
    this.getSession().flush();
    this.desatachar(entity);
    
    mapaEntidadesRemovidas.get(true).stream()
      .forEach(r -> this.getSession().delete(this.consultarGenerica(r.getId(), r.getClass())));
    
    this.getSession().flush();
    return entity;
  }

  protected void excluir(E entity, Usuario usuario) {
    this.getSession().delete(entity);
    this.getSession().flush();
  }

  @SuppressWarnings("rawtypes")
  protected Criteria criarCriterio(Class classe) {
    DetachedCriteria dc = DetachedCriteria.forClass(classe);
    return dc.getExecutableCriteria(getSession());
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  protected E consultar(I id, Class classe) {
    Criteria criteria = this.criarCriterio(classe);
    criteria.add(Restrictions.idEq(id));
    return (E) criteria.uniqueResult();
  }

  protected void desatachar(E entity) {
    entityManager.detach(entity);
  }

  @SuppressWarnings("rawtypes")
  protected AbstractEntity consultarGenerica(Object id, Class classe) {
    Criteria criteria = this.criarCriterio(classe);
    criteria.add(Restrictions.idEq(id));
    return (AbstractEntity) criteria.uniqueResult();
  }
}
