package br.com.aaas.bancohoras.infra;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;

/**
 * Esta classe abstrata permite persistir mapear uma ENUM. <br>
 * Crie um teste unitário que verifica se o valor class é igual ao caminho da enum
 */
public class PersistentEnumType implements UserType, ParameterizedType {

  /**
   * Classe definida no parâmetro "class"
   */
  @SuppressWarnings("rawtypes")
  private Class enumClass;

  /**
   * @exception PersistentEnumTypeException
   */
  @Override
  public void setParameterValues(Properties parameters) {
    if (parameters != null) {
      String className = parameters.getProperty("class");
      try {
        this.enumClass = Class.forName(className);
      } catch (ClassNotFoundException e) {
        throw new PersistentEnumTypeException("A classe ".concat(className).concat(" não foi encontrada"), e);
      }
    }
  }

  @Override
  public Object assemble(Serializable cached, Object owner) throws HibernateException {
    return cached;
  }

  @Override
  public Object deepCopy(Object value) throws HibernateException {
    return value;
  }

  @Override
  public Serializable disassemble(Object value) throws HibernateException {
    return (Serializable) value;
  }

  @Override
  public boolean equals(Object x, Object y) throws HibernateException {
    return x == y;
  }

  @Override
  public int hashCode(Object x) throws HibernateException {
    return x == null ? 0 : x.hashCode();
  }

  @Override
  public boolean isMutable() {
    return false;
  }

  @Override
  public Object replace(Object original, Object target, Object owner) throws HibernateException {
    return original;
  }

  @SuppressWarnings("rawtypes")
  @Override
  public Class returnedClass() {
    return this.enumClass;
  }

  @Override
  public int[] sqlTypes() {
    return new int[] { Types.INTEGER };
  }

  /**
   * pega o valor do atributo no banco e retorna a enum correspondente
   */
  @SuppressWarnings("rawtypes")
  @Override
  public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {

    String idString = rs.getString(names[0]);

    if (rs.wasNull()) {
      return null;
    }

    for (Object value : enumClass.getEnumConstants()) {
      PersistentEnum valueConvertidoParaEnumType = (PersistentEnum) value;
      String stringCodigo = String.valueOf(valueConvertidoParaEnumType.getValorQueDeveSerPersistido());

      if (idString.toUpperCase().equals(stringCodigo.toUpperCase())) {
        return value;
      }
    }

    throw new IllegalStateException("O código " + idString + " retornado do banco de dados não existe na enum "
        + returnedClass().getSimpleName());
  }

  

  /**
   * Pega o valor de enum e salva no atributo da tabela
   */
  @SuppressWarnings("rawtypes")
  @Override
  public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
    if (value == null) {
      st.setObject(index, null);
    } else {
      PersistentEnum persistentEnum = ((PersistentEnum) value);
      Class tipoParaPersistir = persistentEnum.getValorQueDeveSerPersistido().getClass();
      // apenas integer e string estão previstos
      if (tipoParaPersistir.equals(Integer.class)) {
        st.setInt(index, (Integer) ((PersistentEnum) value).getValorQueDeveSerPersistido());
      } else if (tipoParaPersistir.equals(String.class)) {
        st.setString(index, (String) ((PersistentEnum) value).getValorQueDeveSerPersistido());
      }
    }
  }
}
