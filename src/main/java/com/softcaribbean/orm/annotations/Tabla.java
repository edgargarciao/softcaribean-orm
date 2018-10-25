package com.softcaribbean.orm.annotations;


public @interface Tabla {

  String nombre() default "";

  String catalogo() default "";


  String esquema() default "";

}
