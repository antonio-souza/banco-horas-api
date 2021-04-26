package br.com.aaas.bancohoras.domain.dto;

import java.io.Serializable;

import br.com.aaas.bancohoras.domain.entity.Usuario;

public class UsuarioDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long              id;

  private String            matricula;

  private String            nome;

  private SetorDTO          setor;

  private String            senha;

  private PerfilDTO         perfil;

  private String            token;

  public UsuarioDTO() {
    super();
  }

  public UsuarioDTO(Usuario usuario) {
    super();
    this.id = usuario.getId();
    this.matricula = usuario.getMatricula();
    this.nome = usuario.getNome();
    this.setor = new SetorDTO(usuario.getSetor());
    this.senha = usuario.getSenha();
    this.perfil = new PerfilDTO(usuario.getPerfil());
    this.token = usuario.getToken();
  }

  public Long getId() {
    return id;
  }

  public String getMatricula() {
    return matricula;
  }

  public String getNome() {
    return nome;
  }

  public SetorDTO getSetor() {
    return setor;
  }

  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

  public PerfilDTO getPerfil() {
    return perfil;
  }

  public String getToken() {
    return token;
  }
}
