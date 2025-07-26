package space.pickly.global.util;

import static space.pickly.global.constant.UrlConstant.*;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.server.Cookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import space.pickly.global.constant.JwtConstant;

@Component
public class CookieUtil {

    public void addTokenCookies(HttpServletResponse response, String accessToken, String refreshToken) {
        ResponseCookie accessTokenCookie = generateTokenCookie(JwtConstant.ACCESS_TOKEN.getCookieName(), accessToken);

        ResponseCookie refreshTokenCookie =
                generateTokenCookie(JwtConstant.REFRESH_TOKEN.getCookieName(), refreshToken);

        response.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
    }

    private ResponseCookie generateTokenCookie(String cookieName, String tokenValue) {
        return ResponseCookie.from(cookieName, tokenValue)
                .path("/")
                .secure(true)
                .sameSite(Cookie.SameSite.LAX.attributeValue())
                .domain(ROOT_DOMAIN)
                .httpOnly(true)
                .build();
    }

    public void deleteCookie(jakarta.servlet.http.Cookie cookie, HttpServletResponse response) {
        cookie.setPath("/");
        cookie.setValue("");
        cookie.setMaxAge(0);
        cookie.setDomain(ROOT_DOMAIN);
        response.addCookie(cookie);
    }
}
