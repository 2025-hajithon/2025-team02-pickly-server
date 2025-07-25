package space.pickly.global.exception;

import static org.springframework.http.HttpStatus.*;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // COMMON
    SERVER_ERROR(INTERNAL_SERVER_ERROR),
    ;

    private final HttpStatus status;
}
