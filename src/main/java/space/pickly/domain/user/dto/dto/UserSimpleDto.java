package space.pickly.domain.user.dto.dto;

import com.querydsl.core.annotations.QueryProjection;
import space.pickly.domain.user.domain.User;

@QueryProjection
public record UserSimpleDto(Long userId, String nickname, String profileImageUrl) {
    public static UserSimpleDto from(User user) {
        return new UserSimpleDto(user.getId(), user.getNickname(), user.getProfileImageUrl());
    }
}
