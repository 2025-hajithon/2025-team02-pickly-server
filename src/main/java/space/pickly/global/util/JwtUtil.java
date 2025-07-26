package space.pickly.global.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.RegisteredClaims;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import java.time.Instant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import space.pickly.domain.auth.domain.AccessTokenDto;
import space.pickly.domain.auth.dto.dto.RefreshTokenDto;
import space.pickly.global.property.JwtProperty;

@Slf4j
@Component
public class JwtUtil {

    private final JwtProperty jwtProperty;
    private final Algorithm accessTokenAlgorithm;
    private final Algorithm refreshTokenAlgorithm;
    private final JWTVerifier accessTokenVerifier;
    private final JWTVerifier refreshTokenVerifier;

    public JwtUtil(JwtProperty jwtProperty) {
        this.jwtProperty = jwtProperty;
        this.accessTokenAlgorithm = Algorithm.HMAC512(jwtProperty.getAccessTokenSecret());
        this.refreshTokenAlgorithm = Algorithm.HMAC512(jwtProperty.getRefreshTokenSecret());
        this.accessTokenVerifier = buildAccessTokenVerifier(jwtProperty, accessTokenAlgorithm);
        this.refreshTokenVerifier = buildRefreshTokenVerifier(jwtProperty, refreshTokenAlgorithm);
    }

    private static JWTVerifier buildAccessTokenVerifier(JwtProperty jwtProperty, Algorithm accessTokenAlgorithm) {
        return JWT.require(accessTokenAlgorithm)
                .withIssuer(jwtProperty.getIssuer())
                .withClaim(RegisteredClaims.SUBJECT, (userId, ignored) -> Long.parseLong(userId.asString()) > 0)
                .build();
    }

    private static JWTVerifier buildRefreshTokenVerifier(JwtProperty jwtProperty, Algorithm refreshTokenAlgorithm) {
        return JWT.require(refreshTokenAlgorithm)
                .withIssuer(jwtProperty.getIssuer())
                .withClaim(RegisteredClaims.SUBJECT, (userId, ignored) -> Long.parseLong(userId.asString()) > 0)
                .build();
    }

    public AccessTokenDto generateAccessToken(Long userId, Instant issuedAt, Instant expiresAt) {
        String accessTokenValue = JWT.create()
                .withIssuer(jwtProperty.getIssuer())
                .withSubject(userId.toString())
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .sign(accessTokenAlgorithm);

        return AccessTokenDto.of(accessTokenValue, userId);
    }

    public RefreshTokenDto generateRefreshToken(Long userId, Instant issuedAt, Instant expiresAt) {
        String refreshToken = JWT.create()
                .withIssuer(jwtProperty.getIssuer())
                .withSubject(userId.toString())
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .sign(refreshTokenAlgorithm);

        return RefreshTokenDto.of(refreshToken, userId);
    }

    public AccessTokenDto parseAccessToken(String accessToken) throws JWTVerificationException {
        DecodedJWT decodedJWT = accessTokenVerifier.verify(accessToken);

        return AccessTokenDto.of(decodedJWT.getToken(), Long.parseLong(decodedJWT.getSubject()));
    }

    public RefreshTokenDto parseRefreshToken(String refreshToken) throws JWTVerificationException {
        DecodedJWT decodedJWT = refreshTokenVerifier.verify(refreshToken);

        return RefreshTokenDto.of(decodedJWT.getToken(), Long.parseLong(decodedJWT.getSubject()));
    }

    public AccessTokenDto parseOrReissueAccessToken(String accessToken, Instant issuedAt, Instant expiresAt) {
        try {
            return parseAccessToken(accessToken);
        } catch (TokenExpiredException e) {
            DecodedJWT decodedJWT = JWT.decode(accessToken);

            Long userId = Long.parseLong(decodedJWT.getSubject());

            return generateAccessToken(userId, issuedAt, expiresAt);
        }
    }
}
