package br.com.aaas.bancohoras.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.hibernate.Hibernate;

import br.com.aaas.bancohoras.domain.dto.UsuarioCadastroDTO;
import br.com.aaas.bancohoras.domain.dto.UsuarioDTO;
import br.com.aaas.bancohoras.domain.entity.Usuario;
import br.com.aaas.bancohoras.domain.entity.UsuarioPagina;
import br.com.aaas.bancohoras.domain.enums.PerfilEnum;
import br.com.aaas.bancohoras.infra.NaoAutenticadoException;
import br.com.aaas.bancohoras.infra.NaoAutorizadoException;
import br.com.aaas.bancohoras.repository.UsuarioRepository;

@Stateless
public class UsuarioService {

  @Inject
  private UsuarioRepository    usuarioRepository;

  @Inject
  private UsuarioLogadoService usuarioLogadoService;

  public Usuario consultarMatricula(String matricula) {
    Usuario usuario = usuarioRepository.consultarMatricula(matricula);
    if (usuario == null) {
      return null;
    }
    Hibernate.initialize(usuario.getSetor());
    return usuario;
  }

  public Usuario cadastrarSenha(UsuarioCadastroDTO usuarioCadastroDTO) {
    this.exigir(usuarioCadastroDTO.getMatricula(), "Informe a Matrícula.");

    Usuario usuario = usuarioRepository.consultarMatricula(usuarioCadastroDTO.getMatricula());
    this.exigirUsuarioCadastrado(usuario);

    usuario.cadastrarSenha(usuarioCadastroDTO);
    usuario = usuarioRepository.altera(usuario, usuario);

    usuario.setIp(usuarioCadastroDTO.getIp());

    String token = usuarioLogadoService.adicionar(usuario);
    usuario.setToken(token);

    return usuario;
  }

  public Usuario efetuarLogin(String matricula, String senha, String ip) {
    this.exigir(matricula, "Informe a Matrícula.");
    this.exigir(senha, "Informe a Senha.");
    this.exigir(ip, "Informe o IP.");

    Usuario usuario = usuarioRepository.consultarMatricula(matricula);
    this.exigirUsuarioCadastrado(usuario);

    usuario.autenticar(senha);
    usuario.setIp(ip);

    String token = usuarioLogadoService.adicionar(usuario);
    usuario.setToken(token);

    return usuario;
  }

  public Usuario autenticar(String token) {
    this.exigir(token, "Informe o Token.");

    Usuario usuario = usuarioLogadoService.consultar(token);
    this.exigirUsuarioAutenticado(usuario);

    return usuario;
  }

  private void exigirUsuarioCadastrado(Usuario usuario) {
    if (usuario == null) {
      throw new NaoAutorizadoException("Informe a Matrícula.");
    }
  }

  private void exigirUsuarioAutenticado(Usuario usuario) {
    if (usuario == null) {
      throw new NaoAutenticadoException("Token inválido. Refaça o login.");
    }
  }

  private void exigir(String campo, String mensagem) {
    if (campo == null || campo.isEmpty()) {
      throw new IllegalArgumentException(mensagem);
    }
  }

  public UsuarioPagina listar(Usuario usuarioLogado, String criterio, Integer tamanho, Integer pagina) {
    if (!PerfilEnum.ADMINISTRADOR.equals(usuarioLogado.getPerfil())) {
      throw new NaoAutorizadoException("Usuário não autorizado!");
    }
    return usuarioRepository.listar(criterio, tamanho, pagina);
  }

  public void gravar(Usuario usuarioLogado, UsuarioDTO usuarioDTO) {
    if (!PerfilEnum.ADMINISTRADOR.equals(usuarioLogado.getPerfil())) {
      throw new NaoAutorizadoException("Usuário não autorizado!");
    }
    Usuario usuario = usuarioRepository.consultar(Long.valueOf(usuarioDTO.getId()));
    usuario.alterar(usuarioDTO);
    usuarioRepository.altera(usuario, usuarioLogado);
  }
}
