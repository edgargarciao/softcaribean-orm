package com.softcaribbean.orm.JDBC;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;


public class Database {

  private DataSource dataSource;

  public Database(String driver, String url,String usuario,String contraseña){
    
    try {
      Class.forName(driver);
    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    System.out.println(url.split(":")[1]);
    
    dataSource = new DriverManagerDataSource(url,usuario,contraseña);
    
  }
  
  
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
   * This method implements INSERT/UPDATE/DELETE query execution logic in Database System using
   * Spring-JDBC.
   * 
   * @param query Text that represents DML to be executed.
   * @param parameterMap Object containing all parameters required to bind SQL variables.
   * @return the number of rows affected.
   * @throws AppException If there is any problem in the execution.
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
  
  public static void main(String[] args) {
    Database d = new Database("com.mysql.cj.jdbc.Driver","jdbc:mysql://localhost:3306/SoftCaribbeanDatabase ", "root", "root");
    
    d.ejecutarDml("INSERT INTO estudiante(nombre,fecha) VALUES('juan','1994-10-02')");
  }
}
