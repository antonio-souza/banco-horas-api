package br.com.aaas.bancohoras.domain.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.aaas.bancohoras.domain.dto.PontoDTO;
import br.com.aaas.bancohoras.domain.enums.TipoSaldoEnum;
import br.com.aaas.bancohoras.infra.AbstractEntity;

@Entity
@Table(name = "bhr_ponto")
public class Ponto extends AbstractEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @SequenceGenerator(name = "Ponto_SEQ", sequenceName = "bhr_ponto_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Ponto_SEQ")
  @Column(name = "id_ponto")
  private Long              id;

  @JoinColumn(name = "id_periodo")
  @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
  private Periodo           periodo;

  @JoinColumn(name = "id_funcionario")
  @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
  private Funcionario       funcionario;

  @JoinColumn(name = "id_ponto", nullable = false, updatable = true, insertable = true)
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @OrderBy("tipo")
  private List<SaldoPonto>  saldos;

  @Transient
  private SaldoPonto        saldoBancoAcumulado;

  @Transient
  private SaldoPonto        saldoGreveAcumulado;

  public void inicializar() {
    saldos.isEmpty();
  }

  @Override
  public Long getId() {
    return id;
  }

  public List<SaldoPonto> getSaldos() {
    return saldos;
  }

  public SaldoPonto getSaldoBancoAcumulado() {
    return saldoBancoAcumulado;
  }

  public SaldoPonto getSaldoGreveAcumulado() {
    return saldoGreveAcumulado;
  }

  public String getSaldoBancoAcumuladoFormatado() {
    return saldoBancoAcumulado == null ? "+000:00" : saldoBancoAcumulado.getHoraComSinal();
  }

  public String getSaldoGreveAcumuladoFormatado() {
    return saldoGreveAcumulado == null ? "+000:00" : saldoGreveAcumulado.getHoraComSinal();
  }

  public SaldoPonto getSaldoCalculado() {
    return saldos.isEmpty() ? new SaldoPonto(TipoSaldoEnum.DATA_COMEMORATIVA, "+000:00") : saldos.get(TipoSaldoEnum.DATA_COMEMORATIVA.ordinal());
  }

  public SaldoPonto getSaldoBanco() {
    return saldos.isEmpty() ? new SaldoPonto(TipoSaldoEnum.BANCO_HORAS, "+000:00") : saldos.get(TipoSaldoEnum.BANCO_HORAS.ordinal());
  }

  public SaldoPonto getSaldoGreve() {
    return saldos.isEmpty() ? new SaldoPonto(TipoSaldoEnum.GREVE, "+000:00") : saldos.get(TipoSaldoEnum.GREVE.ordinal());
  }

  public String getSaldoBancoFormatado() {
    return "+000:00".equals(getSaldoBanco().getHoraComSinal()) ? null : getSaldoBanco().getHoraComSinal();
  }

  public String getSaldoGreveFormatado() {
    return "+000:00".equals(getSaldoGreve().getHoraComSinal()) ? null : getSaldoGreve().getHoraComSinal();
  }

  public Periodo getPeriodo() {
    return periodo;
  }

  public Funcionario getFuncionario() {
    return funcionario;
  }

  public void setarSaldosAcumulados(Ponto pontoAnterior) {
    SaldoPonto saldoBanco = getSaldoBanco() == null || getSaldoBanco().getHoraComSinal() == null ? new SaldoPonto(TipoSaldoEnum.BANCO_HORAS, "+000:00") : getSaldoBanco();
    SaldoPonto saldoGreve = getSaldoGreve() == null || getSaldoGreve().getHoraComSinal() == null ? new SaldoPonto(TipoSaldoEnum.GREVE, "+000:00") : getSaldoGreve();

    if (pontoAnterior == null) {
      this.saldoBancoAcumulado = saldoBanco;
      this.saldoGreveAcumulado = saldoGreve;

    } else {
      this.saldoBancoAcumulado = saldoBanco.adicionar(pontoAnterior.saldoBancoAcumulado);
      this.saldoGreveAcumulado = saldoGreve.adicionar(pontoAnterior.saldoGreveAcumulado);
    }
  }

  public void gravarSaldos(PontoDTO pontoDTO) {
    this.criarSaldos();
    this.getSaldoBanco().alterar(pontoDTO.getSaldoBanco());
    this.getSaldoGreve().alterar(pontoDTO.getSaldoGreve());
  }

  private void criarSaldos() {
    if (this.saldos.isEmpty()) {
      this.saldos.add(new SaldoPonto(TipoSaldoEnum.DATA_COMEMORATIVA, "+000:00"));
      this.saldos.add(new SaldoPonto(TipoSaldoEnum.BANCO_HORAS, "+000:00"));
      this.saldos.add(new SaldoPonto(TipoSaldoEnum.GREVE, "+000:00"));
    }
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((funcionario == null) ? 0 : funcionario.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((periodo == null) ? 0 : periodo.hashCode());
    result = prime * result + ((saldoBancoAcumulado == null) ? 0 : saldoBancoAcumulado.hashCode());
    result = prime * result + ((saldoGreveAcumulado == null) ? 0 : saldoGreveAcumulado.hashCode());
    result = prime * result + ((saldos == null) ? 0 : saldos.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Ponto other = (Ponto) obj;
    if (funcionario == null) {
      if (other.funcionario != null)
        return false;
    } else if (!funcionario.equals(other.funcionario))
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (periodo == null) {
      if (other.periodo != null)
        return false;
    } else if (!periodo.equals(other.periodo))
      return false;
    if (saldoBancoAcumulado == null) {
      if (other.saldoBancoAcumulado != null)
        return false;
    } else if (!saldoBancoAcumulado.equals(other.saldoBancoAcumulado))
      return false;
    if (saldoGreveAcumulado == null) {
      if (other.saldoGreveAcumulado != null)
        return false;
    } else if (!saldoGreveAcumulado.equals(other.saldoGreveAcumulado))
      return false;
    if (saldos == null) {
      if (other.saldos != null)
        return false;
    } else if (!saldos.equals(other.saldos))
      return false;
    return true;
  }
}
