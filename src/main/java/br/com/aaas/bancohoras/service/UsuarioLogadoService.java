package br.com.aaas.bancohoras.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.inject.Singleton;

import br.com.aaas.bancohoras.domain.entity.Usuario;

@Singleton
public class UsuarioLogadoService {
  
  private Map<String, Usuario> usuarios = new HashMap<String, Usuario>();
  
  public String adicionar(Usuario usuario) {
    String token = UUID.randomUUID().toString();
    usuarios.put(token, usuario);
    return token;
  }
  
  public Usuario consultar(String token) {    
    return usuarios.get(token);
  }
}
