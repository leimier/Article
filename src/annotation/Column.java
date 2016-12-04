package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Dragon on 2016/11/28.
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
    public String field();  // 字段名称
    public boolean primayKey() default false;  //是否为主键
    public String type() default "VARCHAR(80)"; //字段类型
    public boolean defaultNull() default true;  //是否允许为空
}
