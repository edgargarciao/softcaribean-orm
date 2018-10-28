package com.softcaribbean.orm.classes;

import java.util.Date;
import java.util.Set;
import com.softcaribbean.orm.annotations.Columna;
import com.softcaribbean.orm.annotations.Entidad;
import com.softcaribbean.orm.annotations.Id;
import com.softcaribbean.orm.annotations.Tabla;
import com.softcaribbean.orm.annotations.UnoAMuchos;

@Entidad(nombre="programa")
@Tabla
public class Programa {

  @Id
  private int idP;
  
  @Columna(nombre = "nombrePrograma")
  private String nombre;
  
  @Columna
  private String nombreDirector;
  
  @Columna
  private Date fechaApertura;
  
  @UnoAMuchos(tabla="alumno")
  public Set<Alumno> alumnos;
  
  public Programa(int idP, String nombre, String nombreDirector, Date fechaApertura) {
    super();
    this.idP = idP;
    this.nombre = nombre;
    this.nombreDirector = nombreDirector;
    this.fechaApertura = fechaApertura;
  }

  public String getNombreDirector() {
    return nombreDirector;
  }

  public void setNombreDirector(String nombreDirector) {
    this.nombreDirector = nombreDirector;
  }

  public int getIdP() {
    return idP;
  }

  public void setIdP(int idP) {
    this.idP = idP;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Date getFechaApertura() {
    return fechaApertura;
  }

  public void setFechaApertura(Date fechaApertura) {
    this.fechaApertura = fechaApertura;
  }

  public Set<Alumno> getAlumnos() {
    return alumnos;
  }

  public void setAlumnos(Set<Alumno> alumnos) {
    this.alumnos = alumnos;
  }
  
}
