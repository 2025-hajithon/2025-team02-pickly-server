package space.pickly.global.util;

import static space.pickly.global.constant.ProfileConstant.*;

import java.util.Objects;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import space.pickly.global.constant.ProfileConstant;

@Component
@RequiredArgsConstructor
public class ProfileUtil {

    private final Environment environment;

    public ProfileConstant getCurrentProfile() {
        return Stream.of(environment.getActiveProfiles())
                .map(ProfileConstant::from)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(LOCAL);
    }

    public boolean isProdProfile() {
        return getCurrentProfile() == PROD;
    }

    public boolean isDevProfile() {
        return getCurrentProfile() == DEV;
    }

    public boolean isProdOrDevProfile() {
        ProfileConstant currentProfile = getCurrentProfile();
        return currentProfile == PROD || currentProfile == DEV;
    }

    public boolean isDevOrLocalProfile() {
        ProfileConstant currentProfile = getCurrentProfile();
        return currentProfile == DEV || currentProfile == LOCAL;
    }
}
