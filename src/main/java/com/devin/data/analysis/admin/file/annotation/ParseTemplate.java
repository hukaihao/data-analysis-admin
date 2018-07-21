package com.devin.data.analysis.admin.file.annotation;

import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ParseTemplate {

    public String description() default StringUtils.EMPTY;

    public boolean nullable() default false;

    public String ruleRegex() default StringUtils.EMPTY;

    public String ruleDesc() default StringUtils.EMPTY;

    public String formatter() default StringUtils.EMPTY;
}
