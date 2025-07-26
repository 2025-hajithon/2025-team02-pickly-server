package space.pickly.global.exception;

import static org.springframework.http.HttpStatus.*;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // COMMON
    SERVER_ERROR(INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다."),
    AUTH_ACCESS_DENIED(UNAUTHORIZED, "접근 권한이 없습니다."),
    INVALID_REFRESH_TOKEN_USED(UNAUTHORIZED, "비정상적인 리프레시 토큰입니다."),
    ;

    private final HttpStatus status;
    private final String message;
}
