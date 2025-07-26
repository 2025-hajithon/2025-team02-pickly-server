package space.pickly.global.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityConstant {

    public static final String JWT_ROLE_CLAIM_NAME = "role";
    public static final String ACCESS_TOKEN_HEADER_PREFIX = "Bearer ";
    public static final String REFRESH_TOKEN_HEADER = "refresh-token";
    public static final String DEFAULT_ROLE_NAME = "ROLE_USER";
}
