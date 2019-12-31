package com.jxdinfo.hussar.util.bigdataexcel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wenqingkuan
 * @date 2019-10-21 11:26
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyWorkbook {
    String cell();
    String format() default "";
}
