package com.softcaribbean.orm.factory;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import com.softcaribbean.orm.classes.Estudiante;
import com.softcaribbean.orm.constants.ORM;
import com.softcaribbean.orm.util.ClassMgr;
import com.softcaribbean.orm.util.XmlMgr;

public class Configuracion {

  private DatosConexion datosConexion;
  private Properties propiedades;
  private XmlMgr xmlMgr;
  private Map<Class, Map<String, Object>> clases;
  private ClassMgr classMgr;

  public Configuracion(String fileName) {

    propiedades = new XmlMgr().getProperties(fileName);
    loadConfiguration();
  }


  private void loadConfiguration() {

    String nombrePaqueteEscanear = propiedades.getProperty(ORM.MAPEADOR);
 
    List<Class<?>> classes = ClassMgr.getInstance().getClassesInPackage(nombrePaqueteEscanear);
    clases = ClassMgr.getInstance().cargarAnotaciones(clases,classes);
    

  }

  public SesionFactoria crearSesionFactoria() {
    
    return new SesionFactoria(propiedades,clases);
  }

  public static void main(String[] args) {

    new Configuracion("application.xml").crearSesionFactoria().guardar(new Estudiante(1000,"Miguel", new Date()));
  }


}
