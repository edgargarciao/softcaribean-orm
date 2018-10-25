package com.softcaribbean.orm.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface MuchosAUno {


  Class targetEntity() default void.class;

  // CascadeType[] cascade() default {};

  // FetchType fetch() default EAGER;

  boolean optional() default true;
}
