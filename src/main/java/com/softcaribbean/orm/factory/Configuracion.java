package com.softcaribbean.orm.factory;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import org.reflections.Reflections;
import com.softcaribbean.util.XmlMgr;

public class Configuracion {

  private DatosConexion datosConexion;
  public Properties properties;
  public XmlMgr xmlMgr;


  public Configuracion(String fileName) {

    properties = new XmlMgr().getProperties(fileName);
    loadConfiguration();
  }

  private static final char PKG_SEPARATOR = '.';

  private static final char DIR_SEPARATOR = '/';

  private static final String CLASS_FILE_SUFFIX = ".class";

  private static final String BAD_PACKAGE_ERROR =
      "Unable to get resources from path '%s'. Are you sure the package '%s' exists?";

  private void loadConfiguration() {
    System.out.println(properties.getProperty(XmlMgr.MAPEADOR));
    // Reflections reflections = new Reflections(properties.getProperty(XmlMgr.MAPEADOR));

    String scannedPackage = properties.getProperty(XmlMgr.MAPEADOR);

    String scannedPath = scannedPackage.replace(PKG_SEPARATOR, DIR_SEPARATOR);
    URL scannedUrl = Thread.currentThread().getContextClassLoader().getResource(scannedPath);
    if (scannedUrl == null) {
      throw new IllegalArgumentException(
          String.format(BAD_PACKAGE_ERROR, scannedPath, scannedPackage));
    }
    File scannedDir = new File(scannedUrl.getFile());
    List<Class<?>> classes = new ArrayList<Class<?>>();
    for (File file : scannedDir.listFiles()) {
      classes.addAll(find(file, scannedPackage));
    }

    for(Class clase:classes) {
      System.out.println(Arrays.asList(clase.getClassLoader()));
    }
    
    

  }

  private static List<Class<?>> find(File file, String scannedPackage) {
    List<Class<?>> classes = new ArrayList<Class<?>>();
    String resource = scannedPackage + PKG_SEPARATOR + file.getName();
    if (file.isDirectory()) {
      for (File child : file.listFiles()) {
        classes.addAll(find(child, resource));
      }
    } else if (resource.endsWith(CLASS_FILE_SUFFIX)) {
      int endIndex = resource.length() - CLASS_FILE_SUFFIX.length();
      String className = resource.substring(0, endIndex);
      try {
        classes.add(Class.forName(className));
      } catch (ClassNotFoundException ignore) {
      }
    }
    return classes;
  }

  public SesionFactoria crearSesionFactoria() {



    return new SesionFactoria(properties);
  }

  public static void main(String[] args) {
    Configuracion c = new Configuracion("application.xml");
  }


}
