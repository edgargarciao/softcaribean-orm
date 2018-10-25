package com.softcaribbean.orm.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(TYPE)
@Retention(RUNTIME)
public @interface Tabla {

  String nombre() default "";

  String catalogo() default "";


  String esquema() default "";

}
