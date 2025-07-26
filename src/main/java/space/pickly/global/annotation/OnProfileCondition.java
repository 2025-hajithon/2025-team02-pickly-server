package space.pickly.global.annotation;

import static java.util.Objects.*;

import java.util.Arrays;
import java.util.Map;
import lombok.NonNull;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import space.pickly.global.constant.ProfileConstant;

public class OnProfileCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, @NonNull AnnotatedTypeMetadata metadata) {
        String[] activeProfiles = context.getEnvironment().getActiveProfiles();
        ProfileConstant[] targetProfiles = getTargetProfiles(metadata);

        return Arrays.stream(targetProfiles)
                .anyMatch(targetProfile -> Arrays.asList(activeProfiles).contains(targetProfile.getValue()));
    }

    private ProfileConstant[] getTargetProfiles(AnnotatedTypeMetadata metadata) {
        Map<String, Object> attributes =
                requireNonNull(metadata.getAnnotationAttributes(ConditionalOnProfile.class.getName()));
        return (ProfileConstant[]) attributes.get("value");
    }
}
