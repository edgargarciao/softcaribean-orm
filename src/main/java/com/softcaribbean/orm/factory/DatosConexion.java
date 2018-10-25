package com.softcaribbean.orm.factory;

public class DatosConexion {

  public String url;
  public String driver_class;
  public String userNameDB;
  public String passwordDB;

  public DatosConexion(String url, String driver_class, String userNameDB, String passwordDB) {
    super();
    this.url = url;
    this.driver_class = driver_class;
    this.userNameDB = userNameDB;
    this.passwordDB = passwordDB;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getDriver_class() {
    return driver_class;
  }

  public void setDriver_class(String driver_class) {
    this.driver_class = driver_class;
  }

  public String getUserNameDB() {
    return userNameDB;
  }

  public void setUserNameDB(String userNameDB) {
    this.userNameDB = userNameDB;
  }

  public String getPasswordDB() {
    return passwordDB;
  }

  public void setPasswordDB(String passwordDB) {
    this.passwordDB = passwordDB;
  }



}
