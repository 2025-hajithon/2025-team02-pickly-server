package space.pickly.domain.auth.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import space.pickly.domain.auth.domain.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>, CustomRefreshTokenRepository {

    Optional<RefreshToken> findByToken(String token);

    void deleteByToken(String token);

    void deleteAllByUserId(Long userId);
}
