package iot.lviv.ua.model.Annotation;

import java.lang.annotation.*;

@Target(value = ElementType.FIELD)
@Retention(value= RetentionPolicy.RUNTIME)
public @interface PrimaryKey {
}
