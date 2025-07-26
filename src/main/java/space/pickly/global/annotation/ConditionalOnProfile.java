package space.pickly.global.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Conditional;
import space.pickly.global.constant.ProfileConstant;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Conditional({OnProfileCondition.class})
public @interface ConditionalOnProfile {
    ProfileConstant[] value() default {ProfileConstant.LOCAL};
}
