package space.pickly.global.util;

import static space.pickly.global.exception.ErrorCode.*;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import space.pickly.domain.user.dao.UserRepository;
import space.pickly.domain.user.domain.User;
import space.pickly.global.auth.CustomUserDetails;
import space.pickly.global.exception.CustomException;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserUtil {

    private final UserRepository userRepository;

    public User getCurrentUser() {
        return userRepository.findById(getCurrentUserId()).orElseThrow(() -> CustomException.from(USER_NOT_FOUND));
    }

    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        validateAuthenticationNotNull(authentication);

        try {
            return Long.parseLong(authentication.getName());
        } catch (NumberFormatException e) {
            log.error("[UserUtil] 현재 사용자 ID 파싱 실패: name={}", authentication.getName());
            throw CustomException.from(AUTH_NOT_PARSABLE);
        }
    }

    public Optional<CustomUserDetails> getCurrentUserDetails() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)
                .map(CustomUserDetails.class::cast);
    }

    private void validateAuthenticationNotNull(Authentication authentication) {
        if (authentication == null) {
            log.error("[UserUtil] 시큐리티 인증 정보 비어있음");
            throw CustomException.from(AUTH_NOT_EXIST);
        }
    }
}
