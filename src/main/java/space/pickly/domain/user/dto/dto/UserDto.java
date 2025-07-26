package space.pickly.domain.user.dto.dto;

import java.time.LocalDateTime;
import space.pickly.domain.user.domain.User;

public record UserDto(
        Long id,
        String nickname,
        String profileImageUrl,
        String oauthId,
        String oauthProvider,
        String oauthEmail,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
    public static UserDto from(User user) {
        return new UserDto(
                user.getId(),
                user.getNickname(),
                user.getProfileImageUrl(),
                user.getOauthId(),
                user.getOauthProvider(),
                user.getOauthEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt());
    }
}
