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
import com.softcaribbean.orm.annotations.MuchosAUno;
import com.softcaribbean.orm.annotations.Tabla;
import com.softcaribbean.orm.annotations.ValorGenerado;
import com.softcaribbean.orm.constants.ORM;

public class ClassMgr {

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


  /**
   * Metodo que permite buscar dentro de un paquete todas las clases existentes.
   * @param packageName Nombre del paquete
   * @return Las clases que se encuentran dentro del paquete.
   */
  public List<Class<?>> getClassesInPackage(String packageName) {
    String scannedPath = packageName.replace(ORM.PKG_SEPARATOR, ORM.DIR_SEPARATOR);

    URL scannedUrl = Thread.currentThread().getContextClassLoader().getResource(scannedPath);

    if (scannedUrl == null) {
      throw new IllegalArgumentException(
          String.format(ORM.BAD_PACKAGE_ERROR, scannedPath, packageName));
    }

    File scannedDir = new File(scannedUrl.getFile());

    List<Class<?>> classes = new ArrayList<Class<?>>();

    for (File file : scannedDir.listFiles()) {
      classes.addAll(find(file, packageName));
    }

    return classes;
  }

  /**
   * Metodo que dado un archivo y nombre del paquete valida forma una ruta dentro de la cual
   * buscara todas las clases
   * @param file Carpeta en el sistema con las clases 
   * @param scannedPackage Nombre inicial de la ruta
   * @return Retorna todas las clases que se encuentran dentro de la ruta formada por el nombre de
   *         la ruta y el nombre de la carpeta
   */
  private static List<Class<?>> find(File file, String scannedPackage) {
    List<Class<?>> classes = new ArrayList<Class<?>>();
    String resource = scannedPackage + ORM.PKG_SEPARATOR + file.getName();
    if (file.isDirectory()) {
      for (File child : file.listFiles()) {
        classes.addAll(find(child, resource));
      }
    } else if (resource.endsWith(ORM.CLASS_FILE_SUFFIX)) {
      int endIndex = resource.length() - ORM.CLASS_FILE_SUFFIX.length();
      String className = resource.substring(0, endIndex);
      try {
        classes.add(Class.forName(className));
      } catch (ClassNotFoundException ignore) {
      }
    }
    return classes;
  }

  /**
   * Metodo que lee con JAVA REFLECTION las clases y las gurda dentro del modelo.
   * @param clases Modelo a llenarse con la configuracion de anotaciones realizada por el desarrollador.
   * @param classes Lista de clases que se encuentran dentro del paquete.
   * @return El modelo lleno.
   */
  public Map<Class, Map<String, Object>> cargarAnotaciones(Map<Class, Map<String, Object>> clases,
      List<Class<?>> classes) {

    clases = new HashMap();

    for (Class<?> clase : classes) {

      Map<String, Object> mapaDeConfiguracion = new HashMap<String, Object>();

      mapaDeConfiguracion = cargarEntidad(clase, mapaDeConfiguracion);

      List<String> campos = new ArrayList<String>();
      Map<String, String> nombreCampos = new HashMap<String, String>();
      Map<String, Boolean> camposNulos = new HashMap<String, Boolean>();
      Map<String, Map<Class, Map<String, Object>>> camposMapaForaneos = new HashMap<String, Map<Class, Map<String, Object>>>();
      Map<String,String> camposForaneos = new HashMap<String,String>();
      
      // Se cargan los atributos
      for (Field campo : clase.getDeclaredFields()) {

        String nombreAtributoBD = campo.getName();
        for (Annotation anotacion : campo.getAnnotations()) {
          if (anotacion instanceof Id) {
            mapaDeConfiguracion.put(ORM.ID, nombreAtributoBD);
            campos.add(nombreAtributoBD);
            nombreCampos.put(campo.getName(), nombreAtributoBD);
          } else if (anotacion instanceof ValorGenerado) {

            mapaDeConfiguracion.put(ORM.VALOR_GENERADO, nombreAtributoBD);
          } else if (anotacion instanceof Columna) {
            Columna columna = (Columna) anotacion;

            if (columna.nombre() != null && !columna.nombre().equals("")) {
              nombreAtributoBD = columna.nombre();
            }

            camposNulos.put(campo.getName(), columna.nulo());
            campos.add(nombreAtributoBD);
            nombreCampos.put(campo.getName(), nombreAtributoBD);
          } else if(anotacion instanceof MuchosAUno) {
            camposForaneos.put(campo.getName(),((MuchosAUno)anotacion).nombre());
            nombreAtributoBD = ((MuchosAUno)anotacion).nombre();
            List<Class<?>> classesExt = new ArrayList<Class<?>>();
            classesExt.add(campo.getType());
            System.out.println(campo.getType());
            camposMapaForaneos.put(campo.getName(), cargarAnotaciones(new HashMap<Class, Map<String,Object>>(),classesExt));
            campos.add(nombreAtributoBD);
            nombreCampos.put(campo.getName(), nombreAtributoBD);
          }
        }
      }

      mapaDeConfiguracion.put(ORM.FORANEOS, camposForaneos);
      mapaDeConfiguracion.put(ORM.MAPA_FORANEOS, camposMapaForaneos);
      mapaDeConfiguracion.put(ORM.COLUMNA_NULA, camposNulos);
      mapaDeConfiguracion.put(ORM.COLUMNA, campos);
      mapaDeConfiguracion.put(ORM.NOMBRE_COLUMNA, nombreCampos);

      clases.put(clase, mapaDeConfiguracion);

    }
    return clases;
  }

  /**
   * Metodo que permite cargar el nombre de la tabla
   * 
   * @param clase Con toda la información
   * @param mapaDeConfiguracion A llenarse para obtener el modelo
   * @return El mapa de configuracion con la informacion
   */
  private Map<String, Object> cargarEntidad(Class<?> clase,
      Map<String, Object> mapaDeConfiguracion) {

    for (Annotation anotacion : clase.getAnnotations()) {
      if (anotacion.annotationType().equals(Entidad.class)) {
        Entidad entidad = (Entidad) anotacion;
        mapaDeConfiguracion.put(ORM.ENTIDAD, entidad.nombre());
      }
      if (anotacion.annotationType().equals(Tabla.class)) {
        Tabla tabla = (Tabla) anotacion;
        if (!tabla.nombre().equals("")) {
          mapaDeConfiguracion.put(ORM.TABLA, tabla.nombre());
        }
      }
    }

    if (mapaDeConfiguracion.get(ORM.ENTIDAD) == null) {
      throw new RuntimeException(ORM.ERROR_SOFTCARIBBEAN_ENTIDAD_NULA);
    }
    if ( (
            mapaDeConfiguracion.get(ORM.ENTIDAD).equals("") &&
            mapaDeConfiguracion.get(ORM.TABLA) == null
         )
      || 
        (
            mapaDeConfiguracion.get(ORM.TABLA) != null      && 
            mapaDeConfiguracion.get(ORM.TABLA).equals("")   && 
            mapaDeConfiguracion.get(ORM.ENTIDAD).equals("")
        )
       ){
      
      mapaDeConfiguracion.put(ORM.ENTIDAD, clase.getName());
    
    }

    return mapaDeConfiguracion;
  }

  @Override
  public ClassMgr clone() {
    try {
      throw new CloneNotSupportedException();
    } catch (CloneNotSupportedException ex) {
      System.out.println("No se puede clonar un objeto de la clase "+this.getClass().getName());
    }
    return null;
  }
}
