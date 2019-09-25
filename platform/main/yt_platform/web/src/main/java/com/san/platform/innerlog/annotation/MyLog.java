package com.san.platform.innerlog.annotation;

import java.lang.annotation.*;

/**
 * 自定义注解类
 * 将需要录入审计的方法上标明注解
 * 使用方法@MyLog(value = "方法体")
 */
@Target({ElementType.METHOD})  //注解放置的目标位置,METHOD是可注解在方法级别上
@Retention(RetentionPolicy.RUNTIME) //注解在哪个阶段执行
@Documented //生成文档
public @interface MyLog {
    String value() default "66";
}
