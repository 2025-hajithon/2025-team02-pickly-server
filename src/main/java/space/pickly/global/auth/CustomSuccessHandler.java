package space.pickly.global.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import space.pickly.domain.auth.application.JwtService;
import space.pickly.domain.auth.domain.AccessTokenDto;
import space.pickly.domain.auth.dto.dto.RefreshTokenDto;
import space.pickly.global.util.CookieUtil;

@Slf4j
@RequiredArgsConstructor
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final CookieUtil cookieUtil;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        // 토큰 생성 후 쿠키에 저장
        Long userId = oAuth2User.getUserId();
        AccessTokenDto accessTokenDto = jwtService.createAccessToken(userId);
        RefreshTokenDto refreshTokenDto = jwtService.createRefreshToken(userId);
        cookieUtil.addTokenCookies(response, accessTokenDto.tokenValue(), refreshTokenDto.tokenValue());

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
