package com.softcaribbean.orm.core;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import com.softcaribbean.orm.JDBC.Database;
import com.softcaribbean.orm.constants.ORM;
import com.softcaribbean.orm.dto.ResultadoDB;

public class SesionFactoria {

  private Database database;
  Map<Class, Map<String, Object>> clasesMap;

  public SesionFactoria(Properties propiedades, Map<Class, Map<String, Object>> clasesMap) {
    this.database = new Database(propiedades.getProperty(ORM.SOFTCARIBBEAN_DRIVER_CLASS),
        propiedades.getProperty(ORM.SOFTCARIBBEAN_URL),
        propiedades.getProperty(ORM.SOFTCARIBBEAN_USERNAME),
        propiedades.getProperty(ORM.SOFTCARIBBEAN_PASSWORD));
    this.clasesMap = clasesMap;
  }

  public int guardar(Object objeto) {
    return getSqlInserFromObject(objeto);
  }

  private int getSqlInserFromObject(Object objeto) {

    Map<String, Object> mapa = clasesMap.get(objeto.getClass());

    /* AGREGO EL NOMBRE DE LA TABLA */
    String sql = "INSERT INTO " + getNombreTabla(mapa.get(ORM.ENTIDAD), mapa.get(ORM.TABLA)) + "(";

    /* AGREGO EL NOMBRE DE LAS COLUMNAS */
    String idColumna = getIdColumna(mapa.get(ORM.ID));

    String columnaVG = (String) mapa.get(ORM.VALOR_GENERADO);
    List<String> columnas = (List<String>) mapa.get(ORM.COLUMNA);
    String values = "";
    for (String columna : columnas) {
      if (columna.equals(idColumna)) {
        if (!columna.equals(columnaVG)) {
          sql += columna + ",";
          values += ":" + columna + ",";
        }
      } else {
        sql += columna + ",";
        values += ":" + columna + ",";
      }
    }
    sql = sql.substring(0, sql.length() - 1) + ") VALUES("
        + values.substring(0, values.length() - 1) + ")";

    /* AGREGO LOS VALORES DE LAS COLUMNAS */
    String idField = "";
    MapSqlParameterSource mapaParametrosSql = new MapSqlParameterSource();
    Map<String, String> nombreCampos = (Map<String, String>) mapa.get(ORM.NOMBRE_COLUMNA);
    Map<String, Boolean> camposNulos = (Map<String, Boolean>) mapa.get(ORM.COLUMNA_NULA);
    Map<String, Map<Class, Map<String, Object>>> camposMapaForaneos = (Map<String, Map<Class, Map<String, Object>>>) mapa.get(ORM.MAPA_FORANEOS);
    Map<String,String> foraneos = (Map<String,String>) mapa.get(ORM.FORANEOS);
    
      
    for (Field field : objeto.getClass().getDeclaredFields()) {
      field.setAccessible(true);
      String nombreCampoValor = nombreCampos.get(field.getName());

      if (nombreCampoValor != null && nombreCampoValor.equals(idColumna) && columnaVG != null
          && !columnaVG.equals("")) {
        idField = field.getName();
      }
      
      if (nombreCampoValor != null && !nombreCampoValor.equals("")) {
        try {

          Object valor = field.get(objeto);
          if (camposNulos.get(field.getName()) != null && !camposNulos.get(field.getName())
              && valor == null) {
            throw new RuntimeException(String.format(ORM.COLUMNA_NULA, field.getName()));
          }
          
          Object valueFinal = field.get(objeto);
          
          if(foraneos.containsKey(field.getName())) {
            Map<Class, Map<String, Object>> claseMapa = camposMapaForaneos.get(field.getName());
            String campoId = (String) claseMapa.get(field.getType()).get(ORM.ID);
            
            try {
              Object c = field.get(objeto);
              
              for (Field field2 : c.getClass().getDeclaredFields()) {
                field2.setAccessible(true);
                if(field2.getName().equals(campoId)) {
                  valueFinal = field2.get(c);
                  //mapaParametrosSql.addValue(foraneos.get(field.getName()),valueFinal );
                }
              
              }
              
            } catch (IllegalArgumentException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            } catch (IllegalAccessException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
          }
          
            mapaParametrosSql.addValue(nombreCampoValor,valueFinal );
         
        } catch (IllegalArgumentException e) {
          e.printStackTrace();
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        }
      }
    }

    ResultadoDB resultado = database.ejecutarDmlConLlave(sql, mapaParametrosSql);

    if (resultado.getLlaveGenerada() != -1) {
      for (Field field : objeto.getClass().getDeclaredFields()) {
        field.setAccessible(true);
        if (field.getName().equals(idField)) {
          try {

            if (field.get(objeto) instanceof Long) {
              field.set(objeto, resultado.getLlaveGenerada());
            } else if (field.get(objeto) instanceof Integer) {
              field.set(objeto, ((int) (resultado.getLlaveGenerada())));
            }
            if (field.get(objeto) instanceof Short) {
              field.set(objeto, ((short) (resultado.getLlaveGenerada())));
            }
            if (field.get(objeto) instanceof Byte) {
              field.set(objeto, ((byte) (resultado.getLlaveGenerada())));
            }


          } catch (IllegalArgumentException e) {
            e.printStackTrace();
          } catch (IllegalAccessException e) {
            e.printStackTrace();
          }
        }
      }
    }


    return resultado.getResultado();
  }

  private String getNombreTabla(Object entidad, Object tabla) {

    if (tabla != null) {
      return (String) ((entidad == null || entidad.equals("")) ? tabla : entidad);
    }

    return (String) ((entidad == null || entidad.equals("")) ? tabla : entidad);
  }

  public int actualizar(Object objeto) {
    Map<String, Object> mapa = clasesMap.get(objeto.getClass());

    /* AGREGO EL NOMBRE DE LA TABLA */
    String sql = "UPDATE "
        + ((mapa.get(ORM.ENTIDAD) == null || mapa.get(ORM.ENTIDAD).equals("")) ? mapa.get(ORM.TABLA)
            : mapa.get(ORM.ENTIDAD))
        + " SET ";


    /* AGREGO EL NOMBRE DE LAS COLUMNAS */
    String idColumna = getIdColumna(mapa.get(ORM.ID));;
    String columnaVG = (String) mapa.get(ORM.VALOR_GENERADO);
    List<String> columnas = (List<String>) mapa.get(ORM.COLUMNA);
    String values = "";
    for (String columna : columnas) {
      if (columna.equals(idColumna)) {
        if (!columna.equals(columnaVG)) {
          sql += columna + ",";
        }
      } else {
        sql += columna + " = :" + columna + " ,";

      }
    }
    sql = sql.substring(0, sql.length() - 1) + " WHERE " + idColumna + " = :" + idColumna;

    /* AGREGO LOS VALORES DE LAS COLUMNAS */
    MapSqlParameterSource mapaParametrosSql = new MapSqlParameterSource();
    Map<String, String> nombreCampos = (Map<String, String>) mapa.get(ORM.NOMBRE_COLUMNA);
    Map<String, Boolean> camposNulos = (Map<String, Boolean>) mapa.get(ORM.COLUMNA_NULA);

    for (Field field : objeto.getClass().getDeclaredFields()) {
      field.setAccessible(true);
      String nombreCampoValor = nombreCampos.get(field.getName());
      if (nombreCampoValor != null && !nombreCampoValor.equals("")) {
        try {
          Object valor = field.get(objeto);
          if (camposNulos.get(field.getName()) != null && !camposNulos.get(field.getName())
              && valor == null) {
            throw new RuntimeException(String.format(ORM.COLUMNA_NULA, field.getName()));
          }
          mapaParametrosSql.addValue(nombreCampoValor, field.get(objeto));
        } catch (IllegalArgumentException e) {
          e.printStackTrace();
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        }
      }
    }

    return database.ejecutarDml(sql, mapaParametrosSql);
  }

  public int eliminar(Object objeto) {
    Map<String, Object> mapa = clasesMap.get(objeto.getClass());

    /* AGREGO EL NOMBRE DE LA TABLA */
    String sql = "DELETE FROM "
        + ((mapa.get(ORM.ENTIDAD) == null || mapa.get(ORM.ENTIDAD).equals("")) ? mapa.get(ORM.TABLA)
            : mapa.get(ORM.ENTIDAD));


    /* AGREGO EL NOMBRE DE LAS COLUMNAS */
    String idColumna = getIdColumna(mapa.get(ORM.ID));

    sql += " WHERE " + idColumna + " = :" + idColumna;

    /* AGREGO LOS VALORES DE LAS COLUMNAS */
    MapSqlParameterSource mapaParametrosSql = new MapSqlParameterSource();
    Map<String, String> nombreCampos = (Map<String, String>) mapa.get(ORM.NOMBRE_COLUMNA);
    Map<String, Boolean> camposNulos = (Map<String, Boolean>) mapa.get(ORM.COLUMNA_NULA);

    for (Field field : objeto.getClass().getDeclaredFields()) {
      field.setAccessible(true);
      String nombreCampoValor = nombreCampos.get(field.getName());
      if (nombreCampoValor != null && !nombreCampoValor.equals("")) {
        try {


          mapaParametrosSql.addValue(nombreCampoValor, field.get(objeto));
        } catch (IllegalArgumentException e) {
          e.printStackTrace();
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        }
      }
    }

    return database.ejecutarDml(sql, mapaParametrosSql);
  }

  private String getIdColumna(Object object) {
    if (object == null) {

    }
    return (String) object;
  }

}
