package com.softcaribbean.orm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import com.softcaribbean.orm.core.Configuracion;

@RunWith(JUnit4.class)
public class XmlTest {
  
  @Test
  public void givenAXmlWithWrongFormateInTheDriverParameterThen() throws Exception{
    // Act
    new Configuracion("application_wrong_driver.xml").crearSesionFactoria();
  
  }
  
  @Test
  public void givenAXmlWithWrongFormateInTheURLParameterThen() throws Exception {
    // Act
    new Configuracion("application_wrong_url.xml").crearSesionFactoria();
  
  }
  
  @Test
  public void givenAXmlWithWrongFormateInTheUserParameterThen() throws Exception {
    // Act
    new Configuracion("application_wrong_user.xml").crearSesionFactoria();
  
  }
  
  @Test
  public void givenAXmlWithWrongFormateInThePasswordParameterThen() throws Exception {
    // Act
    new Configuracion("application_wrong_password.xml").crearSesionFactoria();
  
  }
  
}
