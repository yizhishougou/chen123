package com.example.autoconfig;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) // 打在类上
@Retention(RetentionPolicy.RUNTIME)
public @interface ElasticSimpleJob {

    // 注解只能 String  +  基本
    String  jobName() default "";
    String corn() default "";
    int shardingTotalCount() default 1;
    boolean overWrite() default false;

}
