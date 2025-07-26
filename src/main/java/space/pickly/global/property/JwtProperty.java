package space.pickly.global.property;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "jwt")
public class JwtProperty {

    private final String issuer;
    private final String accessTokenSecret;
    private final Long accessTokenExpiration; // seconds
    private final String refreshTokenSecret;
    private final Long refreshTokenExpiration; // seconds
}
