package com.softcaribbean.orm.repository;

import com.softcaribbean.orm.classes.Estudiante;
import com.softcaribbean.orm.factory.Configuracion;
import com.softcaribbean.orm.factory.SesionFactoria;


public class EstudiantesRepositorio {

  // Method Used To Create The Hibernate's SessionFactory Object
  public static SesionFactoria getSessionFactory() {

    // Creating Configuration Instance & Passing Hibernate Configuration File
    Configuracion configObj = new Configuracion("hibernate.cfg.xml");

    

    // Since Hibernate Version 4.x, Service Registry Is Being Used
    // ServiceRegistry serviceRegistryObj = new
    // StandardServiceRegistryBuilder().applySettings(configObj.getProperties()).build();

    // Creating Hibernate Session Factory Instance
    // SessionFactory factoryObj = configObj.buildSessionFactory(serviceRegistryObj);
    // return factoryObj;
    return null;
  }

  public int insertarEstudiante(Estudiante estudiante) {



    return 0;
  }

  public void actualizarEstudiante(Estudiante estudiante) {


  }

  public void eliminarEstudiante(Estudiante estudiante) {


  }
}
