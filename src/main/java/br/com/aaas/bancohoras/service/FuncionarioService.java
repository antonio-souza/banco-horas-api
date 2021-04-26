package br.com.aaas.bancohoras.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.aaas.bancohoras.domain.entity.Funcionario;
import br.com.aaas.bancohoras.infra.DomainException;
import br.com.aaas.bancohoras.repository.FuncionarioRepository;

@Stateless
public class FuncionarioService {

  @Inject
  private FuncionarioRepository funcionarioRepository;

  public Funcionario consultarMatricula(String matricula) {
    return funcionarioRepository.consultarMatricula(matricula);
  }

  public List<Funcionario> listar(String criterio) {
    if (criterio == null) {
      throw new DomainException("Informe o critério de pesquisa.");
    }
    if (criterio.length() < 3) {
      throw new DomainException("Informe o critério de pesquisa com 3 caracteres no mínimo.");
    }
    return funcionarioRepository.listar(criterio);
  }
}
