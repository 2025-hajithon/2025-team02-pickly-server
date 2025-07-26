package space.pickly.domain.user.application;

import static space.pickly.global.exception.ErrorCode.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.pickly.domain.user.dao.UserRepository;
import space.pickly.domain.user.dto.dto.UserDto;
import space.pickly.global.exception.CustomException;
import space.pickly.global.util.UserUtil;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserUtil userUtil;

    @Transactional(readOnly = true)
    public UserDto findCurrentUser() {
        Long userId = userUtil.getCurrentUserId();
        return userRepository
                .findById(userId)
                .map(UserDto::from)
                .orElseThrow(() -> CustomException.from(USER_NOT_FOUND));
    }
}
