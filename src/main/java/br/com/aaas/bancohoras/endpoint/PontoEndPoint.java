package br.com.aaas.bancohoras.endpoint;

import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.aaas.bancohoras.domain.dto.PontoDTO;
import br.com.aaas.bancohoras.domain.dto.PontoPaginaDTO;
import br.com.aaas.bancohoras.domain.entity.Ponto;
import br.com.aaas.bancohoras.domain.entity.PontoPagina;
import br.com.aaas.bancohoras.domain.entity.Usuario;
import br.com.aaas.bancohoras.infra.AbstractEndPoint;
import br.com.aaas.bancohoras.service.PontoService;
import br.com.aaas.bancohoras.service.UsuarioService;

@Path("/ponto")
@Produces(MediaType.APPLICATION_JSON)
public class PontoEndPoint extends AbstractEndPoint {

  @Inject 
  private PontoService   pontoService;

  @Inject
  private UsuarioService usuarioService;

  @GET
  @Path("/consulta/{idPonto}")
  @Produces("application/json; charset=UTF-8")
  public Response consulta(
      final @HeaderParam(HttpHeaders.AUTHORIZATION) String token,
      final @PathParam("idPonto") Long idPonto) {
    try {
      Usuario usuario = usuarioService.autenticar(token);
      Ponto ponto = pontoService.consultar(usuario, idPonto);
      PontoDTO pontoDTO = new PontoDTO(ponto);
      return Response.status(Response.Status.OK).entity(pontoDTO).build();

    } catch (Exception excecao) {
      return this.tratarErro(excecao);
    }
  }

  @GET
  @Path("/lista-com-saldos")
  @Produces("application/json; charset=UTF-8")
  public Response listarComSaldos(
      final @HeaderParam(HttpHeaders.AUTHORIZATION) String token,
      final @QueryParam("matriculaPesquisa") String matriculaPesquisa,
      final @DefaultValue("0") @QueryParam("tamanho") Integer tamanho,
      final @DefaultValue("0") @QueryParam("pagina") Integer pagina) {
    try {
      Usuario usuario = usuarioService.autenticar(token);
      PontoPagina pontoPagina = pontoService.listarComSaldos(usuario, matriculaPesquisa, tamanho, pagina);      
      PontoPaginaDTO pontoPaginaDTO = new PontoPaginaDTO(pontoPagina);
      return Response.status(Response.Status.OK).entity(pontoPaginaDTO).build();

    } catch (Exception excecao) {
      return this.tratarErro(excecao);
    }
  }

  @POST
  @Path("/gravacao-saldo")
  @Produces("application/json; charset=UTF-8")
  public Response gravarSaldos( 
      final @HeaderParam(HttpHeaders.AUTHORIZATION) String token,
      final PontoDTO pontoDTO) {
    try {
      Usuario usuario = usuarioService.autenticar(token);
      pontoService.gravarSaldos(usuario, pontoDTO);
      return Response.status(Response.Status.OK).build();

    } catch (Exception excecao) {
      return this.tratarErro(excecao);
    }
  }

  @GET
  @Path("/impressao-saldos/{matriculaPesquisa}")
  @Produces("application/pdf; charset=UTF-8")
  public Response imprimirSaldos(
      final @HeaderParam(HttpHeaders.AUTHORIZATION) String token,
      final @PathParam("matriculaPesquisa") String matriculaPesquisa) {
    try {
      Usuario usuario = usuarioService.autenticar(token);      
      byte[] bytes = pontoService.imprimirSaldos(usuario, matriculaPesquisa);      
      return Response.ok(bytes).type("application/pdf")
          .header("Content-Disposition", "filename=\"ponto\"")
          .build();

    } catch (Exception excecao) {
      return this.tratarErro(excecao);
    }
  }
}
