package com.softcaribbean.orm.factory;

import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;
import java.util.Properties;
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
    String sql = getSqlInserFromObject(objeto);
    int resultado = 0;// database.ejecutarDml(sql);
    return resultado;
  }

  private String getSqlInserFromObject(Object objeto) {

    Map<String, Object> mapa = clasesMap.get(objeto.getClass());

    String sql =
        "INSERT INTO " + ((mapa.get(ORM.ENTIDAD) == null || mapa.get(ORM.ENTIDAD).equals(""))
            ? mapa.get(ORM.TABLA) : mapa.get(ORM.ENTIDAD)) + "(";
    List<String> columnas = (List<String>) mapa.get(ORM.COLUMNA);
    for (String columna : columnas) {
      sql += columna + ",";
    }
    sql = sql.substring(0,sql.length()-1)+")";
    System.out.println("SQL --> " + sql);
    // TODO Auto-generated method stub
    return null;
  }

  public int actualizar(Object objeto) {
    return 0;
  }

  public int delete(Object objeto) {
    return 0;
  }

}
