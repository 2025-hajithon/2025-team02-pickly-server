package space.pickly.domain.auth.application;

import static space.pickly.global.exception.ErrorCode.*;

import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.pickly.domain.auth.dao.RefreshTokenRepository;
import space.pickly.domain.auth.domain.AccessTokenDto;
import space.pickly.domain.auth.domain.RefreshToken;
import space.pickly.domain.auth.dto.dto.RefreshTokenDto;
import space.pickly.global.exception.CustomException;
import space.pickly.global.property.JwtProperty;
import space.pickly.global.util.JwtUtil;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProperty jwtProperty;

    public AccessTokenDto createAccessToken(Long userId) {
        Instant issuedAt = Instant.now();
        Instant expiresAt = issuedAt.plusSeconds(jwtProperty.getAccessTokenExpiration());
        return jwtUtil.generateAccessToken(userId, issuedAt, expiresAt);
    }

    public RefreshTokenDto createRefreshToken(Long userId) {
        Instant issuedAt = Instant.now();
        Instant expiresAt = issuedAt.plusSeconds(jwtProperty.getRefreshTokenExpiration());
        return jwtUtil.generateRefreshToken(userId, issuedAt, expiresAt);
    }

    public AccessTokenDto createAccessToken(Long userId, Instant issuedAt, Instant expiresAt) {
        return jwtUtil.generateAccessToken(userId, issuedAt, expiresAt);
    }

    public AccessTokenDto parseAccessToken(String accessToken) {
        return jwtUtil.parseAccessToken(accessToken);
    }

    public AccessTokenDto reissueAccessTokenWhenExpired(String accessToken, Instant issuedAt, Instant expiresAt) {
        return jwtUtil.parseOrReissueAccessToken(accessToken, issuedAt, expiresAt);
    }

    @Transactional
    public RefreshTokenDto createRefreshToken(Long userId, Instant issuedAt, Instant expiresAt) {
        RefreshTokenDto dto = jwtUtil.generateRefreshToken(userId, issuedAt, expiresAt);
        RefreshToken refreshToken = RefreshToken.create(dto.tokenValue(), dto.userId());
        refreshTokenRepository.save(refreshToken);
        return dto;
    }

    public RefreshTokenDto parseRefreshToken(String refreshToken) {
        return jwtUtil.parseRefreshToken(refreshToken);
    }

    @Transactional
    public RefreshTokenDto rotateRefreshToken(RefreshTokenDto oldTokenDto, Instant issuedAt, Instant expiresAt) {
        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(oldTokenDto.tokenValue())
                .orElseThrow(() -> CustomException.from(INVALID_REFRESH_TOKEN_USED));

        RefreshTokenDto newTokenDto = jwtUtil.generateRefreshToken(oldTokenDto.userId(), issuedAt, expiresAt);
        refreshToken.update(newTokenDto.tokenValue());

        return newTokenDto;
    }

    @Transactional
    public void revokeRefreshTokensByUserId(Long userId) {
        refreshTokenRepository.deleteAllByUserId(userId);
    }

    @Transactional
    public void revokeRefreshToken(RefreshTokenDto dto) {
        refreshTokenRepository.deleteByToken(dto.tokenValue());
    }
}
