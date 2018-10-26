package com.softcaribbean.orm.util;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.softcaribbean.orm.annotations.Columna;
import com.softcaribbean.orm.annotations.Entidad;
import com.softcaribbean.orm.annotations.Id;
import com.softcaribbean.orm.annotations.Tabla;
import com.softcaribbean.orm.annotations.ValorGenerado;
import com.softcaribbean.orm.constants.ORM;

public class ClassMgr {


  private static final char PKG_SEPARATOR = '.';

  private static final char DIR_SEPARATOR = '/';

  private static final String CLASS_FILE_SUFFIX = ".class";

  private static final String BAD_PACKAGE_ERROR =
      "Unable to get resources from path '%s'. Are you sure the package '%s' exists?";

  // private static ClassMgr classMgr;

  private ClassMgr() {
    // classMgr = new ClassMgr();
  }

  public static ClassMgr getInstance() {
    // if (classMgr == null) {
    // classMgr = new ClassMgr();
    // }
    // return classMgr;
    return new ClassMgr();
  }


  public List<Class<?>> getClassesInPackage(String packageName) {
    String scannedPath = packageName.replace(PKG_SEPARATOR, DIR_SEPARATOR);

    URL scannedUrl = Thread.currentThread().getContextClassLoader().getResource(scannedPath);

    if (scannedUrl == null) {
      throw new IllegalArgumentException(
          String.format(BAD_PACKAGE_ERROR, scannedPath, packageName));
    }

    File scannedDir = new File(scannedUrl.getFile());

    List<Class<?>> classes = new ArrayList<Class<?>>();

    for (File file : scannedDir.listFiles()) {
      classes.addAll(find(file, packageName));
    }

    return classes;
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

  public Map<Class, Map<String, Object>> cargarAnotaciones(Map<Class, Map<String, Object>> clases, List<Class<?>> classes) {

    clases = new HashMap();

    for (Class<?> clase : classes) {

      Map<String, Object> mapaDeConfiguracion = new HashMap<String, Object>();


      // Se carga las anotacion ENTIDAD y TABLA
      for (Annotation anotacion : clase.getAnnotations()) {
        if (anotacion.annotationType().equals(Entidad.class)) {
          Entidad entidad = (Entidad) anotacion;
          mapaDeConfiguracion.put(ORM.ENTIDAD, entidad.nombre());
        }
        if (anotacion.annotationType().equals(Tabla.class)) {
          Tabla tabla = (Tabla) anotacion;
          mapaDeConfiguracion.put(ORM.TABLA, tabla.nombre());
        }
      }

      List<String> campos = new ArrayList<String>();
      Map<String,String> nombreCampos = new HashMap<String, String>();

      // Se cargan los atributos
      for (Field campo : clase.getDeclaredFields()) {
        
        String nombreAtributoBD = campo.getName();
        for (Annotation anotacion : campo.getAnnotations()) {
          if(anotacion instanceof Id){
            mapaDeConfiguracion.put(ORM.ID, nombreAtributoBD);            
          }else if(anotacion instanceof ValorGenerado){
            
            mapaDeConfiguracion.put(ORM.VALOR_GENERADO, nombreAtributoBD); 
            
          }else if(anotacion instanceof Columna){
            Columna columna = (Columna) anotacion;
            
            if(columna.nombre() != null && !columna.nombre().equals("")){
              nombreAtributoBD = columna.nombre();
            }
          }
        }        
        campos.add(nombreAtributoBD);
        nombreCampos.put(campo.getName(), nombreAtributoBD);
      }

      mapaDeConfiguracion.put(ORM.COLUMNA, campos);
      mapaDeConfiguracion.put(ORM.NOMBRE_COLUMNA, nombreCampos);

      clases.put(clase, mapaDeConfiguracion);
     
    }
    System.out.println("reeeee --> "+clases);
    return clases;
  }

  @Override
  public ClassMgr clone() {
    try {
      throw new CloneNotSupportedException();
    } catch (CloneNotSupportedException ex) {
      System.out.println("No se puede clonar un objeto de la clase SoyUnico");
    }
    return null;
  }
}
