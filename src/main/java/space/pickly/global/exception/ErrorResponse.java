package space.pickly.global.exception;

public record ErrorResponse(String errorCodeName) {

    public static ErrorResponse from(ErrorCode errorCode) {
        return new ErrorResponse(errorCode.name());
    }
}
