package br.com.aaas.bancohoras.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;

import br.com.aaas.bancohoras.domain.entity.Periodo;
import br.com.aaas.bancohoras.infra.AbstractRepository;

public class PeriodoRepository extends AbstractRepository<Periodo, Long> {

  @SuppressWarnings("unchecked")
  public List<Periodo> listar() {
    Criteria crit = super.criarCriterio(Periodo.class);
    crit.addOrder(Order.desc("dataInicio"));
    return crit.list();
  }
}
