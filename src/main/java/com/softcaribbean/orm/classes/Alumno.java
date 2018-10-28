package com.softcaribbean.orm.classes;

import java.math.BigDecimal;
import com.softcaribbean.orm.annotations.Columna;
import com.softcaribbean.orm.annotations.Entidad;
import com.softcaribbean.orm.annotations.Id;
import com.softcaribbean.orm.annotations.MuchosAUno;
import com.softcaribbean.orm.annotations.ValorGenerado;

@Entidad(nombre = "alumnoClase")
public class Alumno {

  @Id
  @ValorGenerado
  private long id;

  @Columna(nombre = "nombres")
  private String nombre;

  @Columna(nombre = "codigo")
  private int codigo;

  @Columna
  private BigDecimal dineroGuardado;

  @MuchosAUno(nombre = "idPrograma")
  public Programa programa;

  public Alumno(long id, String nombre, int codigo, BigDecimal dineroGuardado, Programa programa) {
    this.id = id;
    this.nombre = nombre;
    this.codigo = codigo;
    this.dineroGuardado = dineroGuardado;
    this.programa = programa;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public int getCodigo() {
    return codigo;
  }

  public void setCodigo(int codigo) {
    this.codigo = codigo;
  }

  public BigDecimal getDineroGuardado() {
    return dineroGuardado;
  }

  public void setDineroGuardado(BigDecimal dineroGuardado) {
    this.dineroGuardado = dineroGuardado;
  }

  public Programa getPrograma() {
    return programa;
  }

  public void setPrograma(Programa programa) {
    this.programa = programa;
  }

}
