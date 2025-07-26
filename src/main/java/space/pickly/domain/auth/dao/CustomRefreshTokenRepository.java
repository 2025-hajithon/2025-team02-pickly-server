package space.pickly.domain.auth.dao;

import java.time.LocalDateTime;

public interface CustomRefreshTokenRepository {

    long deleteByUpdatedAtBefore(LocalDateTime expirationCutoffTime);
}
