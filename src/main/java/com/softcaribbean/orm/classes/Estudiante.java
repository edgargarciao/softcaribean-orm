package com.softcaribbean.orm.classes;

import java.util.Date;
import com.softcaribbean.orm.annotations.Columna;
import com.softcaribbean.orm.annotations.Entidad;
import com.softcaribbean.orm.annotations.Id;
import com.softcaribbean.orm.annotations.Tabla;
import com.softcaribbean.orm.annotations.ValorGenerado;

@Entidad
@Tabla(nombre = "estudiante")
public class Estudiante {

  @Id
  @ValorGenerado
  private int id;
  
  @Columna(nombre="nombre")
  private String nombres;
  
  @Columna(nombre="fecha")
  private Date fechaNacimiento;

  public Estudiante(int id, String nombres, Date fechaNacimiento) {
    super();
    this.id = id;
    this.nombres = nombres;
    this.fechaNacimiento = fechaNacimiento;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNombres() {
    return nombres;
  }

  public void setNombres(String nombres) {
    this.nombres = nombres;
  }

  public Date getFechaNacimiento() {
    return fechaNacimiento;
  }

  public void setFechaNacimiento(Date fechaNacimiento) {
    this.fechaNacimiento = fechaNacimiento;
  }


}
