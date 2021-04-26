package br.com.aaas.bancohoras.infra;

/**
 * 
 * Esta interface retorna uma enum persistível.
 *
 * @param <T>
 */
public interface PersistentEnum<T> {
  
  /**
   * Valor que deve ser persistido
   * 
   * @return T
   */
  T getValorQueDeveSerPersistido();
}
