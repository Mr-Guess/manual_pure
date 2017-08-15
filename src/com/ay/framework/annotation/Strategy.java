package com.ay.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * @Description 策略注解
 * @date 2013-2-20
 * @author WangXin
 */
@Target({ElementType.TYPE})
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface Strategy {
    /**
     * 表示是否启用（默认启用）
     * @return
     * @return boolean
     */
    public boolean open() default true;
}

