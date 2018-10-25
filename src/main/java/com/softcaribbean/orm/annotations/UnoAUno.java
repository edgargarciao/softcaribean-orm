package com.softcaribbean.orm.annotations;

public @interface UnoAUno {

  Class targetEntity() default void.class;

  boolean optional() default true;
}
