package space.pickly.global.auth;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import space.pickly.domain.user.dao.UserRepository;
import space.pickly.domain.user.domain.User;

@Slf4j
@RequiredArgsConstructor
public class CustomUserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        LocalDateTime now = LocalDateTime.now();

        User user = fetchOrCreate(oAuth2User);
        userRepository.save(user);

        return new CustomOAuth2User(oAuth2User, user);
    }

    private User fetchOrCreate(OAuth2User oAuth2User) {
        return userRepository.findByOauthId(oAuth2User.getName()).orElseGet(() -> registerMember(oAuth2User));
    }

    private User registerMember(OAuth2User oAuth2User) {
        String nickname = generateRandomNickname();

        String profileImageUrl = Optional.ofNullable(oAuth2User.<Map<String, Object>>getAttribute("properties"))
                .map(props -> (String) props.get("profile_image"))
                .orElse(null);

        String email = Optional.ofNullable(oAuth2User.<Map<String, Object>>getAttribute("kakao_account"))
                .map(account -> (String) account.get("email"))
                .orElse(null);

        User guest = User.create(nickname, profileImageUrl, oAuth2User.getName(), "KAKAO", email);
        return userRepository.save(guest);
    }

    private String generateRandomNickname() {
        // "피키" + 랜덤 숫자 4자리 조합
        return "피키" + (int) (Math.random() * 10000);
    }
}
