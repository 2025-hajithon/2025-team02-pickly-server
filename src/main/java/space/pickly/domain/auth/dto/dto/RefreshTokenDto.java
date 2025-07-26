package space.pickly.domain.auth.dto.dto;

public record RefreshTokenDto(String tokenValue, Long userId) {

    public static RefreshTokenDto of(String tokenValue, Long userId) {
        return new RefreshTokenDto(tokenValue, userId);
    }
}
