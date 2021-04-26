package br.com.aaas.bancohoras.domain.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import br.com.aaas.bancohoras.domain.dto.UsuarioCadastroDTO;
import br.com.aaas.bancohoras.domain.dto.UsuarioDTO;
import br.com.aaas.bancohoras.domain.enums.PerfilEnum;
import br.com.aaas.bancohoras.infra.AbstractEntity;
import br.com.aaas.bancohoras.infra.NaoAutorizadoException;

@Entity
@Table(name = "bhr_funcionario")
public class Usuario extends AbstractEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @SequenceGenerator(name = "Funcionario_SEQ", sequenceName = "bhr_funcionario_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Funcionario_SEQ")
  @Column(name = "id_funcionario")
  private Long              id;

  @Column(name = "matricula")
  private String            matricula;

  @Column(name = "nome")
  private String            nome;

  @JoinColumn(name = "id_setor")
  @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
  private Setor             setor;

  @Column(name = "senha")
  private String            senha;

  @Column(name = "cod_perfil")
  @Type(type = "br.com.aaas.bancohoras.infra.PersistentEnumType", parameters = { @Parameter(name = "class", value = "br.com.aaas.bancohoras.domain.enums.PerfilEnum") })
  private PerfilEnum        perfil;

  @Transient
  private String            senhaAntiga;

  @Transient
  private String            senhaNova;

  @Transient
  private String            senhaNovaConfirmacao;

  @Transient
  private String            ip;

  @Transient
  private String            token;

  @Override
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getMatricula() {
    return matricula;
  }

  public void setMatricula(String matricula) {
    this.matricula = matricula;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public Setor getSetor() {
    return setor;
  }

  public void setSetor(Setor setor) {
    this.setor = setor;
  }

  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

  public String getSenhaAntiga() {
    return senhaAntiga;
  }

  public void setSenhaAntiga(String senhaAntiga) {
    this.senhaAntiga = senhaAntiga;
  }

  public String getSenhaNova() {
    return senhaNova;
  }

  public void setSenhaNova(String senhaNova) {
    this.senhaNova = senhaNova;
  }

  public String getSenhaNovaConfirmacao() {
    return senhaNovaConfirmacao;
  }

  public void setSenhaNovaConfirmacao(String senhaNovaConfirmacao) {
    this.senhaNovaConfirmacao = senhaNovaConfirmacao;
  }

  public PerfilEnum getPerfil() {
    return perfil;
  }

  public void setPerfil(PerfilEnum perfil) {
    this.perfil = perfil;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  @Override
  public String toString() {
    return String.format("%s - %s - %s", matricula, nome, setor);
  }

  public void autenticar(String senha) {
    this.exigir(this.senha, "Para acessar o sistema clique em 'Cadastrar Senha'.");
    
    String hash = new Senha(senha).getHash();
    if (!hash.equals(this.senha)) {
      throw new NaoAutorizadoException("Senha inválida.");
    }
  }

  private void exigir(String campo, String mensagem) {
    if (campo == null || campo.isEmpty()) {
      throw new IllegalArgumentException(mensagem);
    }
  }

  public boolean possuiPermissaoMenorOuIgualAdmistrador() {    
    return perfil.getCodigo().compareTo(PerfilEnum.ADMINISTRADOR.getCodigo()) <= 0;
  }

  public boolean possuiPermissaoMenorOuIgualChefe() {    
    return perfil.getCodigo().compareTo(PerfilEnum.CHEFFE.getCodigo()) <= 0;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public void alterar(UsuarioDTO usuarioDTO) {
    this.senha = usuarioDTO.getSenha() == null || usuarioDTO.getSenha().isEmpty() ? null : this.senha;
  }

  public void cadastrarSenha(UsuarioCadastroDTO usuarioCadastroDTO) {
    if (this.senha == null && !usuarioCadastroDTO.getSenhaAtual().isEmpty()) {
      throw new IllegalArgumentException("Não informe a Senha Atual.");
    }
    if (this.senha != null && usuarioCadastroDTO.getSenhaAtual().isEmpty()) {
      throw new IllegalArgumentException("Informe a Senha Atual.");
    }
    String hashAtual = new Senha(usuarioCadastroDTO.getSenhaAtual()).getHash();
    if (this.senha != null && !hashAtual.equals(this.senha)) {
      throw new NaoAutorizadoException("Senha Atual inválida.");
    }
    this.exigir(usuarioCadastroDTO.getSenhaNova(), "Informe a Nova Senha.");
    this.exigir(usuarioCadastroDTO.getSenhaNovaConfirmacao(), "Informe a Confirmação de Nova Senha.");
    if (!usuarioCadastroDTO.getSenhaNova().equals(usuarioCadastroDTO.getSenhaNovaConfirmacao())) {
      throw new IllegalArgumentException("Informe a Nova Senha e Confirmação iguais.");
    }
    this.senha = new Senha(usuarioCadastroDTO.getSenhaNova()).getHash();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((ip == null) ? 0 : ip.hashCode());
    result = prime * result + ((matricula == null) ? 0 : matricula.hashCode());
    result = prime * result + ((nome == null) ? 0 : nome.hashCode());
    result = prime * result + ((perfil == null) ? 0 : perfil.hashCode());
    result = prime * result + ((senha == null) ? 0 : senha.hashCode());
    result = prime * result + ((senhaAntiga == null) ? 0 : senhaAntiga.hashCode());
    result = prime * result + ((senhaNova == null) ? 0 : senhaNova.hashCode());
    result = prime * result + ((senhaNovaConfirmacao == null) ? 0 : senhaNovaConfirmacao.hashCode());
    result = prime * result + ((setor == null) ? 0 : setor.hashCode());
    result = prime * result + ((token == null) ? 0 : token.hashCode());
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
    Usuario other = (Usuario) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (ip == null) {
      if (other.ip != null)
        return false;
    } else if (!ip.equals(other.ip))
      return false;
    if (matricula == null) {
      if (other.matricula != null)
        return false;
    } else if (!matricula.equals(other.matricula))
      return false;
    if (nome == null) {
      if (other.nome != null)
        return false;
    } else if (!nome.equals(other.nome))
      return false;
    if (perfil != other.perfil)
      return false;
    if (senha == null) {
      if (other.senha != null)
        return false;
    } else if (!senha.equals(other.senha))
      return false;
    if (senhaAntiga == null) {
      if (other.senhaAntiga != null)
        return false;
    } else if (!senhaAntiga.equals(other.senhaAntiga))
      return false;
    if (senhaNova == null) {
      if (other.senhaNova != null)
        return false;
    } else if (!senhaNova.equals(other.senhaNova))
      return false;
    if (senhaNovaConfirmacao == null) {
      if (other.senhaNovaConfirmacao != null)
        return false;
    } else if (!senhaNovaConfirmacao.equals(other.senhaNovaConfirmacao))
      return false;
    if (setor == null) {
      if (other.setor != null)
        return false;
    } else if (!setor.equals(other.setor))
      return false;
    if (token == null) {
      if (other.token != null)
        return false;
    } else if (!token.equals(other.token))
      return false;
    return true;
  }
}
