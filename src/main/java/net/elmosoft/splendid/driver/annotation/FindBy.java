package net.elmosoft.splendid.driver.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface FindBy {

	String xpath() default "";

	String css() default "";

	String buttonText() default "";

	String buttonContainsText() default "";

	String linkText() default "";

	String linkContainsText() default "";

	String ngModel() default "";

	String ngRepeat() default "";

	String ngClick() default "";

	String id() default "";

	String className() default "";

	String name() default "";

	String type() default "";

}