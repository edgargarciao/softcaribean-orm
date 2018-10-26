package com.softcaribbean.orm;

import java.util.Date;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import com.softcaribbean.orm.classes.Estudiante;
import com.softcaribbean.orm.core.Configuracion;
import com.softcaribbean.orm.core.SesionFactoria;

/**
 * Unit test for simple App.
 */
@RunWith(JUnit4.class)
public class AppTest{ 

  private static SesionFactoria sesion;
  
  @BeforeClass
  public static void inicializar(){
    sesion = new Configuracion("application.xml").crearSesionFactoria(); 
  }
  
  @Test
  public void givenAObjectNotNullWhenSaveThenStoreTheRegisterInDatabase(){
   // sesion.guardar(new Estudiante(1000, "JUAN", new Date(), 19));
  }
  
  @Test
  public void givenAIncompleteObjectWhenSaveThenStoreTheRegisterInDatabase(){
    //sesion.guardar(new Estudiante(1000, "JUAN", new Date(), 19));
  }
  
  @Test  
  public void givenAObjectNotNullWhenUpdateThenStoreTheRegisterInDatabase(){
    
    Estudiante estudiante = new Estudiante(1000, "JUAN", new Date(), 19);
    sesion.guardar(estudiante);
    System.out.println(estudiante.getId());
    // sesion.
    //sesion.actualizar(estudiante);
  }
}
