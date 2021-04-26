package br.com.aaas.bancohoras.endpoint;

import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.aaas.bancohoras.domain.dto.UsuarioCadastroDTO;
import br.com.aaas.bancohoras.domain.dto.UsuarioDTO;
import br.com.aaas.bancohoras.domain.dto.UsuarioPaginaDTO;
import br.com.aaas.bancohoras.domain.entity.Usuario;
import br.com.aaas.bancohoras.domain.entity.UsuarioPagina;
import br.com.aaas.bancohoras.infra.AbstractEndPoint;
import br.com.aaas.bancohoras.service.UsuarioLogadoService;
import br.com.aaas.bancohoras.service.UsuarioService;

@Path("/usuario")
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioEndPoint extends AbstractEndPoint {

  @Inject
  private UsuarioService       usuarioService;

  @Inject
  private UsuarioLogadoService usuarioLogadoService;

  @GET
  @Path("/consulta")
  @Produces("application/json; charset=UTF-8")
  public Response consultar(final @HeaderParam("matricula") String matricula) {
    try {
      Usuario usuario = usuarioService.consultarMatricula(matricula);
      if (usuario == null) {
        return Response.status(Response.Status.NO_CONTENT).build();
      }      
      UsuarioDTO usuarioDTO = new UsuarioDTO(usuario);
      return Response.status(Response.Status.OK).entity(usuarioDTO).build();

    } catch (Exception excecao) {
      return this.tratarErro(excecao);
    }
  }

  @POST
  @Path("/cadastro/web")
  @Produces("application/json; charset=UTF-8")
  public Response cadastarSenhaWeb(final UsuarioCadastroDTO usuarioCadastroDTO) {
    try {
      Usuario usuario = usuarioService.cadastrarSenha(usuarioCadastroDTO);
      UsuarioDTO usuarioDTO = new UsuarioDTO(usuario);
      usuarioDTO.setSenha(null);
      return Response.status(Response.Status.OK).entity(usuarioDTO).build();

    } catch (Exception excecao) {
      return this.tratarErro(excecao);
    }
  }

  @GET
  @Path("/login/web")
  @Produces("application/json; charset=UTF-8")
  public Response autenticarWeb(
      final @HeaderParam("matricula") String matricula, 
      final @HeaderParam("senha") String senha, 
      final @HeaderParam("ip") String ip) {
    try {
      Usuario usuario = usuarioService.efetuarLogin(matricula, senha, ip);
      UsuarioDTO usuarioDTO = new UsuarioDTO(usuario);
      usuarioDTO.setSenha(null);
      return Response.status(Response.Status.OK).entity(usuarioDTO).build();

    } catch (Exception excecao) {
      return this.tratarErro(excecao);
    }
  }

  @GET
  @Path("/lista")
  @Produces("application/json; charset=UTF-8")
  public Response listar(
      final @HeaderParam(HttpHeaders.AUTHORIZATION) String token, 
      final @QueryParam("criterio") String criterio, 
      final @DefaultValue("0") @QueryParam("tamanho") Integer tamanho, 
      final @DefaultValue("0") @QueryParam("pagina") Integer pagina) {
    try {
      Usuario usuarioLogado = usuarioService.autenticar(token);
      UsuarioPagina usuarioPagina = usuarioService.listar(usuarioLogado, criterio, tamanho, pagina);
      UsuarioPaginaDTO pontoPaginaDTO = new UsuarioPaginaDTO(usuarioPagina);
      return Response.status(Response.Status.OK).entity(pontoPaginaDTO).build();

    } catch (Exception excecao) {
      return this.tratarErro(excecao);
    }
  }

  @POST
  @Path("/gravacao")
  @Produces("application/json; charset=UTF-8")
  public Response gravar(
      final @HeaderParam(HttpHeaders.AUTHORIZATION) String token, 
      final UsuarioDTO usuarioDTO) {
    try {
      Usuario usuarioLogado = usuarioService.autenticar(token);
      usuarioService.gravar(usuarioLogado, usuarioDTO);
      return Response.status(Response.Status.OK).build();

    } catch (Exception excecao) {
      return this.tratarErro(excecao);
    }
  }

  @GET
  @Path("/verificacao-token")
  @Produces("application/json; charset=UTF-8")
  public Response existirToken(final @HeaderParam(HttpHeaders.AUTHORIZATION) String token) {
    try {
      Usuario usuarioLogado = usuarioLogadoService.consultar(token);
      Boolean existeToken = usuarioLogado != null;
      return Response.status(Response.Status.OK).entity(existeToken).build();

    } catch (Exception excecao) {
      return this.tratarErro(excecao);
    }
  }
}
