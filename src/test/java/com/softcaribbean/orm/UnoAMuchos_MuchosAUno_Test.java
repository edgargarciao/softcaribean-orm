package com.softcaribbean.orm;

import java.math.BigDecimal;
import java.util.Date;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import com.softcaribbean.orm.classes.Alumno;
import com.softcaribbean.orm.classes.Programa;
import com.softcaribbean.orm.core.Configuracion;
import com.softcaribbean.orm.core.SesionFactoria;

@RunWith(JUnit4.class)
public class UnoAMuchos_MuchosAUno_Test {

  private static SesionFactoria sesion;
  private Object delete;


  @BeforeClass
  public static void inicializar() {
    sesion = new Configuracion("application.xml").crearSesionFactoria();
  }

  @Test
  public void givenTwoObjectsCompleteWhenCallSaveThenStoreTheRegisterInTheDatabase() {
    // Arrange
    Programa programa =
        new Programa(1, "Ingenieria de sistema", "Pilar Rodriguez Tenjo", new Date());
    sesion.guardar(programa);
    
    Alumno alumno = new Alumno(1500, "INTELIGENTITO MARTINEZ", 1150967, new BigDecimal(1000), programa);    
    alumno.setPrograma(programa);

    // Act
    sesion.guardar(alumno);

    // clean
    delete = alumno;
    terminar();
    delete = programa;
  } 
  
  @Test
  public void givenTwoObjectsCompleteWhenCallDeleteThenRemoveTheRegisterInTheDatabase() {
    // Arrange
    Programa programa =
        new Programa(1, "Ingenieria de sistema", "Pilar Rodriguez Tenjo", new Date());
    sesion.guardar(programa);
    
    Alumno alumno = new Alumno(1500, "INTELIGENTITO MARTINEZ", 1150967, new BigDecimal(1000), programa);    
    alumno.setPrograma(programa);
    sesion.guardar(alumno);
    
    // Act
    sesion.actualizar(alumno);

    // clean
    delete = alumno;
    terminar();
    delete = programa;
  } 
  
  @After
  public void terminar() {
    if(delete != null) {
      // SI QUIERE VER LOS RESULTADOS EN LA BASE DE DATOS PUEDES COMENTAR ESTA LINEA
      sesion.eliminar(delete);
      delete = null;
    }
  }

}
