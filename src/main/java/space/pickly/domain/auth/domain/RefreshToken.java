package space.pickly.domain.auth.domain;

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
public class RefreshToken extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_token_id")
    private Long id;

    @Column(unique = true)
    private String token;

    private Long userId;

    @Builder(access = AccessLevel.PRIVATE)
    private RefreshToken(String token, Long userId) {
        this.token = token;
        this.userId = userId;
    }

    public static RefreshToken create(String token, Long userId) {
        return RefreshToken.builder().token(token).userId(userId).build();
    }

    public void update(String token) {
        this.token = token;
    }
}
