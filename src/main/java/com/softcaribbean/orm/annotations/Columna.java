package com.softcaribbean.orm.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface Columna {


  String nombre() default "";

  boolean unico() default false;

  boolean permiteNulo() default true;

  boolean insertable() default true;

  boolean actualizable() default true;

  String columnDefinition() default "";

  String tabla() default "";

  int tamaño() default 255;

  int precision() default 0;

  int escala() default 0;
}
