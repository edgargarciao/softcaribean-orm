package com.softcaribbean.orm.factory;

import java.util.Properties;
import com.softcaribbean.util.XmlMgr;

public class Configuracion {
  
  private DatosConexion datosConexion;
  public Properties properties;
  public XmlMgr xmlMgr;
  
  
  public Configuracion(String fileName) {

    properties = new XmlMgr().getProperties(fileName);
    loadConfiguration();
  }
  
  
  private void loadConfiguration() {
    
  }


  public SesionFactoria crearSesionFactoria(){
    
    
    
    return new SesionFactoria(properties);
  }

  public static void main(String[] args) {
    Configuracion c = new Configuracion("application.xml");
  }


}
