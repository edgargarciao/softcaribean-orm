package com.softcaribbean.orm.annotations;

public @interface MuchosAUno {


  Class targetEntity() default void.class;

  // CascadeType[] cascade() default {};

  // FetchType fetch() default EAGER;

  boolean optional() default true;
}
