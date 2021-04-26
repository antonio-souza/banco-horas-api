package br.com.aaas.bancohoras.infra;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

public class EntityUtil {

  private EntityUtil() {}

  public static List<AbstractEntity> listarEntidadesEmCascata(Object objeto) {
    return listarEntidadesEmCascata(objeto, new ArrayList<>());
  }

  @SuppressWarnings("rawtypes")
  private static List<AbstractEntity> listarEntidadesEmCascata(Object objeto, List<AbstractEntity> entidades) {
    if (!(objeto instanceof AbstractEntity) && !(objeto instanceof Collection)) {
      return entidades;
    }
    if (objeto instanceof Collection) {
      Collection colecao = (Collection) objeto;
      for (Object objetoDaColecao : colecao) {
        entidades = listarEntidadesEmCascata(objetoDaColecao, entidades);
      }
      return entidades;
    }
    AbstractEntity entidade = (AbstractEntity) objeto;
        
    entidades.add(entidade);
    
    entidades = listarEntidadesEmCascataDosAtributos(entidades, entidade);
    return entidades;
  }

  private static List<AbstractEntity> listarEntidadesEmCascataDosAtributos(List<AbstractEntity> entidades, AbstractEntity entidade) {
    String mensagemErro = "Erro ao listar entidades do atributo %s.%s: %s";
    Field[] atributos = pegarAtributos(entidade);
    
    for (Field atributo : atributos) {
      try {
        OneToOne oneToOne = atributo.getAnnotation(OneToOne.class);
        CascadeType[] cascadeOneToOne = oneToOne == null ? null : oneToOne.cascade();
        ManyToOne manyToOne = atributo.getAnnotation(ManyToOne.class);
        CascadeType[] cascadeManyToOne = manyToOne == null ? null : manyToOne.cascade();
        OneToMany oneToMany = atributo.getAnnotation(OneToMany.class);
        CascadeType[] cascadeOneToMany = oneToMany == null ? null : oneToMany.cascade();
        
        if ((cascadeOneToOne == null || cascadeOneToOne.length == 0) 
            && (cascadeManyToOne == null || cascadeManyToOne.length == 0) 
            && (cascadeOneToMany == null || cascadeOneToMany.length == 0) ) {
          continue;
        }
        Method metodoGet = entidade.getClass().getMethod(obtemNomeMetodoGet(atributo));
        Object valorDoAtributo = metodoGet.invoke(entidade);
        entidades = listarEntidadesEmCascata(valorDoAtributo, entidades);       
      
      } catch (NoSuchMethodException e) {
        
      } catch (Exception e) {
        mensagemErro = String.format(mensagemErro, entidade.getClass().getSimpleName(), atributo.getName(), e);
        throw new IllegalArgumentException(mensagemErro);
      }
    }
    return entidades;
  }

  private static Field[] pegarAtributos(AbstractEntity entidade) {
    if (entidade.getSuperClasseMapeada() != null) {
      return entidade.getSuperClasseMapeada().getDeclaredFields();
    } else {
      return entidade.getClass().getDeclaredFields();
    }    
  }

  private static String obtemNomeMetodoGet(Field field) {
    return "get" + obtemNomeMetodo(field);
  }

  private static String obtemNomeMetodo(Field field) {
    return field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
  }
}
