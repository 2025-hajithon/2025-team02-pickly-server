package space.pickly.domain.auth.dao;

import static space.pickly.domain.auth.domain.QRefreshToken.*;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class CustomRefreshTokenRepositoryImpl implements CustomRefreshTokenRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    @Transactional
    public long deleteByUpdatedAtBefore(LocalDateTime expiredCutoffTime) {
        return queryFactory
                .delete(refreshToken)
                .where(refreshToken.updatedAt.before(expiredCutoffTime))
                .execute();
    }
}
