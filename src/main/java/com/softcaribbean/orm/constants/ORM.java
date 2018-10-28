package com.softcaribbean.orm.constants;

public class ORM {
  
  /**
   * Constantes de mapeo
   */
  public static final String ENTIDAD        = "entidad";
  public static final String TABLA          = "tabla";
  public static final String COLUMNA        = "columna";
  public static final String ID             = "id";
  public static final String VALOR_GENERADO = "id_generado";
  public static final String NOMBRE_COLUMNA = "nombre_columna";
  public static final String COLUMNA_NULA   = "columna_nula";
  public static final String FORANEOS       = "campos_foraneos";
  public static final String MAPA_FORANEOS  = "mapa_foraneos";
  
  public static final String ERROR_SOFTCARIBBEAN_COLUMNA =
      "La columna '%s' no puede ser nula.";
  public static final String ERROR_SOFTCARIBBEAN_ENTIDAD_NULA =
      "La etiqueta entidad no existe en la clase y es obligatoria.";
  
  public static final char PKG_SEPARATOR = '.';
  public static final char DIR_SEPARATOR = '/';
  public static final String CLASS_FILE_SUFFIX = ".class";
  public static final String BAD_PACKAGE_ERROR =
      "No hay recursos dentro del paquete '%s'. Estas seguro de que el paquete '%s'existe?";
  
  /**
   * Constantes de lectura del XML
   */
  public static final String SOFTCARIBBEAN_CONFIGURATION    = "softcaribbean-configuration";
  public static final String SESSION_FACTORIA               = "sesion-factoria";
  public static final String SOFTCARIBBEAN_DRIVER_CLASS     = "softcaribbean.driver_class";
  public static final String SOFTCARIBBEAN_URL              = "softcaribbean.connection.url";
  public static final String SOFTCARIBBEAN_USERNAME         = "softcaribbean.connection.username";
  public static final String SOFTCARIBBEAN_PASSWORD         = "softcaribbean.connection.password";
  public static final String MAPEADOR                       = "mapeador";
  public static final String PROPIEDAD                      = "propiedad";

  public static final String ERROR_SOFTCARIBBEAN_CONFIGURATION =
      "El xml debe iniciar con la etiqueta ";
  public static final String ERROR_SESSION_FACTORIA =
      "Despues de la etiqueta debe contener la etiqueta softcaribbean-configuration";
  

  /**
   * Constantes de la conexión a base de datos
   */
  
  public static final String ERROR_DATABASE_DRIVER_CLASS = "El driver '%s' no es reconocido.";
  
}
