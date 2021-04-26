package br.com.aaas.bancohoras.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

import br.com.aaas.bancohoras.domain.entity.Ponto;
import br.com.aaas.bancohoras.domain.entity.PontoPagina;
import br.com.aaas.bancohoras.domain.entity.Usuario;
import br.com.aaas.bancohoras.infra.AbstractRepository;

public class PontoRepository extends AbstractRepository<Ponto, Long> {
  
  @SuppressWarnings("unchecked")
  public PontoPagina listarComSaldos(String matricula, Integer tamanho, Integer pagina) {    
    Criteria crit = this.montarCriteriaFuncionario(matricula);
    
    crit.addOrder(Order.desc("pe.dataInicio"));
    crit.addOrder(Order.asc("fu.nome"));
    
    List<Ponto> pontos = crit.list();
    
    IntStream.range(0, pontos.size())
      .map(i -> pontos.size() - i - 1)        //Indice reverso - para executar do fim para o inicio
      .forEach(r -> pontos.get(r).setarSaldosAcumulados(r + 1 >= pontos.size() ? null : pontos.get(r + 1))); 
    
    List<Ponto> pontosPaginados = new ArrayList<Ponto>(pontos);
    
    if (tamanho.compareTo(Integer.valueOf(0)) > 0 && pagina.compareTo(Integer.valueOf(0)) > 0) {
      pontosPaginados = IntStream.range(0, pontos.size())
          .filter(i -> i >= (pagina - 1) * tamanho && i <= (pagina - 1) * tamanho + (tamanho - 1)) //Indice Paginado
          .mapToObj(ip -> pontos.get(ip))
          .collect(Collectors.toList());      
    }
    return new PontoPagina(pontosPaginados, pagina, tamanho, Long.valueOf(String.valueOf(pontos.size())));
  }

  private Criteria montarCriteriaFuncionario(String matricula) {
    Criteria crit = super.criarCriterio(Ponto.class);
    crit.createAlias("funcionario", "fu", JoinType.INNER_JOIN);
    crit.createAlias("fu.setor", "se", JoinType.INNER_JOIN);
    crit.createAlias("periodo", "pe", JoinType.INNER_JOIN);
    
    crit.add(Restrictions.eq("fu.matricula", matricula));
    return crit;
  }

  public Ponto consultar(Long idPonto) {
    Ponto ponto = super.consultar(idPonto, Ponto.class);
    ponto.inicializar();
    super.desatachar(ponto);
    return ponto;
  }

  public Ponto consultarSemFases(Long idPonto) {
    Ponto ponto = super.consultar(idPonto, Ponto.class);
    super.desatachar(ponto);
    return ponto;
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public Ponto altera(Ponto ponto, Usuario usuario) {
    return super.alterar(ponto, usuario);
  }
}
