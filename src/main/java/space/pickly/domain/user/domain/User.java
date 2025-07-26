package space.pickly.domain.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import space.pickly.domain.common.model.BaseEntity;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String nickname;

    private String profileImageUrl;

    private String oauthId;

    private String oauthProvider;

    @Builder
    private User(String nickname, String profileImageUrl, String oauthId, String oauthProvider) {
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.oauthId = oauthId;
        this.oauthProvider = oauthProvider;
    }

    public static User create(String nickname, String profileImageUrl, String oauthId, String oauthProvider) {
        return User.builder()
                .nickname(nickname)
                .profileImageUrl(profileImageUrl)
                .oauthId(oauthId)
                .oauthProvider(oauthProvider)
                .build();
    }
}
