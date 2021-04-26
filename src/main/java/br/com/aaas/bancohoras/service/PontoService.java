package br.com.aaas.bancohoras.service;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.aaas.bancohoras.domain.dto.PontoDTO;
import br.com.aaas.bancohoras.domain.entity.Funcionario;
import br.com.aaas.bancohoras.domain.entity.Ponto;
import br.com.aaas.bancohoras.domain.entity.PontoPagina;
import br.com.aaas.bancohoras.domain.entity.Usuario;
import br.com.aaas.bancohoras.domain.enums.PerfilEnum;
import br.com.aaas.bancohoras.infra.NaoAutorizadoException;
import br.com.aaas.bancohoras.infra.RelatorioDTO;
import br.com.aaas.bancohoras.infra.RelatorioExporter;
import br.com.aaas.bancohoras.repository.PontoRepository;

@Stateless
public class PontoService {

  @Inject
  private PontoRepository     pontoRepository;

  @Inject
  private RelatorioExporter   relatorioExporter;
  
  @Inject
  private FuncionarioService funcionarioService;

  public PontoPagina listarComSaldos(Usuario usuario, String matricula, Integer tamanho, Integer pagina) {
    return pontoRepository.listarComSaldos(matricula, tamanho, pagina);
  }

  public Ponto consultar(Usuario usuario, Long idPonto) {
    Ponto ponto = pontoRepository.consultar(idPonto);
    this.verificarAutorizacao(usuario, ponto);
    return ponto;
  }

  public void gravarSaldos(Usuario usuario, PontoDTO pontoDTO) {
    Ponto ponto = pontoRepository.consultar(pontoDTO.getId());
    this.verificarAutorizacao(usuario, ponto);

    ponto.gravarSaldos(pontoDTO);
    pontoRepository.altera(ponto, usuario);
  }

  public byte[] imprimirSaldos(Usuario usuario, String matriculaPesquisa) {
    Funcionario funcionario = this.consultarComAutorizacao(usuario, matriculaPesquisa);
    PontoPagina pontoPagina = pontoRepository.listarComSaldos(matriculaPesquisa, 12, 1);

    pontoPagina.getPontos().sort((p1, p2) -> p1.getPeriodo().getDataInicio().compareTo(p2.getPeriodo().getDataInicio()));

    String caminhoJasper = "/report/bhr-saldo-ponto.jasper";
    RelatorioDTO relatorioDTO = new RelatorioDTO(caminhoJasper, new ArrayList<>(pontoPagina.getPontos()));
    relatorioDTO.addParameter("P_FUNCIONARIO", funcionario.toString());

    ByteArrayOutputStream outputStream = relatorioExporter.exportPdfToStream(relatorioDTO);
    byte[] bytes = outputStream.toByteArray();
    return bytes;
  }

  private Funcionario consultarComAutorizacao(Usuario usuario, String matriculaPesquisa) {
    Funcionario funcionario = funcionarioService.consultarMatricula(matriculaPesquisa);
    if (!PerfilEnum.ADMINISTRADOR.equals(usuario.getPerfil()) && !usuario.getMatricula().equals(funcionario.getMatricula())) {
      throw new NaoAutorizadoException("Usuário não autorizado!");
    }
    return funcionario;
  }

  private void verificarAutorizacao(Usuario usuario, Ponto ponto) {
    if (!PerfilEnum.ADMINISTRADOR.equals(usuario.getPerfil()) && !usuario.getId().equals(ponto.getFuncionario().getId())) {
      throw new NaoAutorizadoException("Usuário não autorizado!");
    }
  }
}
