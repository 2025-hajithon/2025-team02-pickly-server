package space.pickly.global.constant;

import java.util.stream.Stream;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProfileConstant {
    PROD("prod"),
    DEV("dev"),
    LOCAL("local"),
    TEST("test");

    private final String value;

    public static ProfileConstant from(String value) {
        return Stream.of(values())
                .filter(profile -> profile.getValue().equals(value))
                .findFirst()
                .orElse(null);
    }
}
