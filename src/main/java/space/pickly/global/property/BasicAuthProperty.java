package space.pickly.global.property;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "basic-auth")
public class BasicAuthProperty {

    private final String username;
    private final String password;
}
