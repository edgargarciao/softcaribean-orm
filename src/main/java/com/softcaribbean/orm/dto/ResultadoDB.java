package com.softcaribbean.orm.dto;

public class ResultadoDB {

  private int resultado;
  private long llaveGenerada;

  public ResultadoDB() {

  }

  public ResultadoDB(int resultado, long llaveGenerada) {
    super();
    this.resultado = resultado;
    this.llaveGenerada = llaveGenerada;
  }

  public int getResultado() {
    return resultado;
  }

  public void setResultado(int resultado) {
    this.resultado = resultado;
  }

  public long getLlaveGenerada() {
    return llaveGenerada;
  }

  public void setLlaveGenerada(long llaveGenerada) {
    this.llaveGenerada = llaveGenerada;
  }


}
