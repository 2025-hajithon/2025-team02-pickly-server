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

    // AUTH
    AUTH_ACCESS_DENIED(UNAUTHORIZED, "접근 권한이 없습니다."),
    INVALID_REFRESH_TOKEN_USED(UNAUTHORIZED, "비정상적인 리프레시 토큰입니다."),
    INVALID_JWT(UNAUTHORIZED, "유효하지 않은 JWT입니다."),
    AUTH_NOT_PARSABLE(INTERNAL_SERVER_ERROR, "시큐리티 인증 정보를 파싱할 수 없습니다."),
    AUTH_NOT_EXIST(INTERNAL_SERVER_ERROR, "시큐리티 인증 정보가 존재하지 않습니다."),

    // USER
    USER_NOT_FOUND(NOT_FOUND, "사용자를 찾을 수 없습니다."),

    // ARTICLE
    ARTICLE_NOT_FOUND(NOT_FOUND, "게시글을 찾을 수 없습니다."),

    // VOTE
    VOTE_NOT_CREATABLE_VOTE_ENDED(CONFLICT, "투표가 종료된 게시글입니다."),
    VOTE_NOT_CREATABLE_ALREADY_VOTED(CONFLICT, "이미 투표한 게시글입니다."),

    // REACTION
    REACTION_NOT_FOUND(NOT_FOUND, "리액션을 찾을 수 없습니다."),
    REACTION_NOT_CREATABLE_AUTHOR(CONFLICT, "게시글 작성자는 반응을 남길 수 없습니다.");

    private final HttpStatus status;
    private final String message;
}
