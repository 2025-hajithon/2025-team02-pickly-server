package space.pickly.domain.auth.domain;

public record AccessTokenDto(String tokenValue, Long userId) {

    public static AccessTokenDto of(String tokenValue, Long userId) {
        return new AccessTokenDto(tokenValue, userId);
    }
}
