package br.com.aaas.bancohoras.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import br.com.aaas.bancohoras.domain.enums.TipoSaldoEnum;
import br.com.aaas.bancohoras.infra.AbstractEntity;
import br.com.aaas.bancohoras.infra.DomainException;

@Entity
@Table(name = "bhr_saldo_ponto")
public class SaldoPonto extends AbstractEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @SequenceGenerator(name = "Saldo_SEQ", sequenceName = "bhr_saldo_ponto_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Saldo_SEQ")
  @Column(name = "id_saldo_ponto")
  private Long              id;

  @Column(name = "tp_saldo")
  @Type(type = "br.com.aaas.bancohoras.infra.PersistentEnumType", parameters = { @Parameter(name = "class", value = "br.com.aaas.bancohoras.domain.enums.TipoSaldoEnum") })
  private TipoSaldoEnum     tipo;

  @Column(name = "qtd_minutos")
  private Integer           minutos;

  protected SaldoPonto() {
    super();
  }

  public SaldoPonto(TipoSaldoEnum tipo, String horaComSinal) {
    super();
    this.tipo = tipo;
    this.alterar(horaComSinal);
  }

  public SaldoPonto(TipoSaldoEnum tipo, Integer minutos) {
    super();
    this.tipo = tipo;
    this.minutos = minutos;
  }

  @Override
  public Long getId() {
    return id;
  }

  public TipoSaldoEnum getTipo() {
    return tipo;
  }

  public Integer getMinutos() {
    return minutos;
  }

  public void alterar(String horaComSinal) {
    if (horaComSinal == null || horaComSinal.isEmpty()) {
      this.minutos = null;
      return;
    }
    if (!horaComSinal.matches("^[+|-][0-9][0-9][0-9]:[0-5][0-9]$")) {
      throw new DomainException("Formato Inválido. Correto: +/-999:59");
    }
    String sinal = horaComSinal.substring(0, 1);
    String hora = horaComSinal.substring(1, 4);
    String minuto = horaComSinal.substring(5, 7);
    this.minutos = Integer.valueOf(hora) * 60 + Integer.valueOf(minuto);
    if ("-".equals(sinal)) {
      this.minutos = -this.minutos;
    }
  }

  public String getHoraComSinal() {
    if (this.minutos == null) {
      return null;
    }
    String sinal = this.minutos < 0 ? "-" : "+";
    Integer hora = Math.abs(this.minutos) / 60;
    Integer minuto = Math.abs(this.minutos) % 60;
    return String.format("%s%s:%s", sinal, String.format("%03d", hora), String.format("%02d", minuto));
  }

  public SaldoPonto adicionar(SaldoPonto outro) {
    return new SaldoPonto(this.tipo, this.minutos + outro.minutos);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((minutos == null) ? 0 : minutos.hashCode());
    result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
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
    SaldoPonto other = (SaldoPonto) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (minutos == null) {
      if (other.minutos != null)
        return false;
    } else if (!minutos.equals(other.minutos))
      return false;
    if (tipo != other.tipo)
      return false;
    return true;
  }
}
