package com.softcaribbean.orm.factory;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.springframework.core.MethodParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import com.softcaribbean.orm.JDBC.Database;
import com.softcaribbean.orm.constants.ORM;

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
    String sql = "INSERT INTO "
        + ((mapa.get(ORM.ENTIDAD) == null || mapa.get(ORM.ENTIDAD).equals("")) ? mapa.get(ORM.TABLA)
            : mapa.get(ORM.ENTIDAD))
        + "(";


    /* AGREGO EL NOMBRE DE LAS COLUMNAS */
    String idColumna = (String) mapa.get(ORM.ID);
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
    MapSqlParameterSource mapaParametrosSql = new MapSqlParameterSource();    
    Map<String,String> nombreCampos = (Map<String, String>) mapa.get(ORM.NOMBRE_COLUMNA);

    for (Field field : objeto.getClass().getDeclaredFields()) {
      field.setAccessible(true);
      String nombreCampoValor = nombreCampos.get(field.getName());
      if(nombreCampoValor != null && !nombreCampoValor.equals("")) {
        try {
          mapaParametrosSql.addValue(nombreCampoValor, field.get(objeto));
        } catch (IllegalArgumentException e) {
          e.printStackTrace();
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        }
      }
    }

    return database.ejecutarDml(sql,mapaParametrosSql);
  }

  public int actualizar(Object objeto) {
    return 0;
  }

  public int delete(Object objeto) {
    return 0;
  }

}
