package com.softcaribbean.orm.JDBC;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import com.softcaribbean.orm.constants.ORM;
import com.softcaribbean.orm.dto.ResultadoDB;


public class Database {

  private DataSource dataSource;

  /**
   * Consutructor que permite crear la conexion a base de datos
   * @param driver
   * @param url
   * @param usuario
   * @param contraseña
   */
  public Database(String driver, String url,String usuario,String contraseña){
    
    // Coloca en memoria el driver a ser usado para realizar la conexion
    try {
      Class.forName(driver);
    } catch (ClassNotFoundException e) {
      Logger.getLogger(this.getClass().getName()).log(Level.INFO, e.getMessage(), e);
      throw new RuntimeException(String.format(ORM.ERROR_DATABASE_DRIVE_CLASS,driver));
    }
    
    // Crea la conexion a base de datos
    dataSource = new DriverManagerDataSource(url,usuario,contraseña);
    
  }
  
  
  /**
   * Metodo que permite ejecutar una sentencia DML
   * @param sql A ejecutarse.
   * @return La cantidad de filas afectadas despues de la ejecucion de la sentencia.
   */
  public int ejecutarDml(String sql)  {

    try {
      NamedParameterJdbcTemplate namedParameterJdbcTemplate =
          new NamedParameterJdbcTemplate(dataSource);
      
      int filasAfectadas = namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource());

      return filasAfectadas;
    } catch (Exception genericException) {
      Logger.getLogger(this.getClass().getName()).log(Level.INFO, genericException.getMessage(), genericException);
      throw new RuntimeException(genericException);
    }
  }

  /**
   * Metodo que permite ejecutar una sentencia DML dado unos parametros para pasar
   * como variables BIND.
   * @param sql A ejecutarse.
   * @param parameterMap - Mapa de parametros con las variables bind
   * @return La cantidad de filas afectadas despues de la ejecucion de la sentencia.
   */
  public int ejecutarDml(String sql, MapSqlParameterSource  parameterMap) {

    try {
      NamedParameterJdbcTemplate namedParameterJdbcTemplate =
          new NamedParameterJdbcTemplate(dataSource);

      int filasAfectadas = namedParameterJdbcTemplate.update(sql, parameterMap);

      return filasAfectadas;
    } catch (Exception genericException) {
      Logger.getLogger(this.getClass().getName()).log(Level.INFO, genericException.getMessage(), genericException);
      throw new RuntimeException(genericException);
    }
  }
  
  /**
   * Metodo que permite ejecutar una sentencia DML con una llave en especifico.
   * @param query Sentencia A ejecutarse
   * @param parameterMap - Mapa de parametros con las variables bind
   * @return
   */
  public ResultadoDB ejecutarDmlConLlave(String query, MapSqlParameterSource parameterMap) {

    ResultadoDB resultDB = new ResultadoDB();
    NamedParameterJdbcTemplate namedParameterJdbcTemplate =
        new NamedParameterJdbcTemplate(dataSource);
    KeyHolder keyHolder = new GeneratedKeyHolder();
    int affectedRows = namedParameterJdbcTemplate.update(query, parameterMap, keyHolder);
    Long generatedId = keyHolder.getKey().longValue();
    resultDB.setResultado(affectedRows);
    resultDB.setLlaveGenerada(generatedId);
    return resultDB;
  }

}
