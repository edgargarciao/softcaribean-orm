package com.softcaribbean.orm.repository;

import com.softcaribbean.orm.classes.Estudiante;
import com.softcaribbean.orm.factory.Configuracion;
import com.softcaribbean.orm.factory.SesionFactoria;


public class EstudiantesRepositorio {

  
  public static SesionFactoria getSessionFactory() {

    SesionFactoria sesion = new Configuracion("application.xml").crearSesionFactoria();

    return sesion;
  }

  public int insertarEstudiante(Estudiante estudiante) {
    SesionFactoria sesion = getSessionFactory();


    return 0;
  }

  public void actualizarEstudiante(Estudiante estudiante) {


  }

  public void eliminarEstudiante(Estudiante estudiante) {


  }
}
