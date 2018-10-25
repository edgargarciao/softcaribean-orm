package com.softcaribbean.JDBC;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;


public class Database {

  private DataSource dataSource;

  public Database(String url,String usuario,String contraseña){
    dataSource = new DriverManagerDataSource(url,usuario,contraseña);
  }
  
  
  public int executeDml(String query)  {

    try {
      NamedParameterJdbcTemplate namedParameterJdbcTemplate =
          new NamedParameterJdbcTemplate(dataSource);

      MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
      int affectedRows = namedParameterJdbcTemplate.update(query, mapSqlParameterSource);

      return affectedRows;
    } catch (Exception genericException) {
      Logger.getLogger(this.getClass().getName()).log(Level.INFO, genericException.getMessage(), genericException);
      throw new RuntimeException(genericException);
    }
  }

  /**
   * This method implements INSERT/UPDATE/DELETE query execution logic in Database System using
   * Spring-JDBC.
   * 
   * @param query Text that represents DML to be executed.
   * @param parameterMap Object containing all parameters required to bind SQL variables.
   * @return the number of rows affected.
   * @throws AppException If there is any problem in the execution.
   */
  
  public int executeDml(String query, MapSqlParameterSource  parameterMap) {

    try {
      NamedParameterJdbcTemplate namedParameterJdbcTemplate =
          new NamedParameterJdbcTemplate(dataSource);

      int affectedRows = namedParameterJdbcTemplate.update(query, parameterMap);

      return affectedRows;
    } catch (Exception genericException) {
      Logger.getLogger(this.getClass().getName()).log(Level.INFO, genericException.getMessage(), genericException);
      throw new RuntimeException(genericException);
    }
  }
}
