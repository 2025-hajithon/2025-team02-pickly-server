package space.pickly.global.auth;

import lombok.Getter;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import space.pickly.domain.user.domain.User;

@Getter
public class CustomOAuth2User extends DefaultOAuth2User {

    private final Long userId;

    public CustomOAuth2User(OAuth2User oAuth2User, User user) {
        super(oAuth2User.getAuthorities(), oAuth2User.getAttributes(), "id");
        this.userId = user.getId();
    }
}
