package space.pickly.domain.user.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import space.pickly.domain.user.application.UserService;
import space.pickly.domain.user.dto.dto.UserDto;

@Tag(name = "[User]", description = "사용자 API")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "내 정보 확인", description = "현재 로그인된 사용자 정보를 확인합니다.")
    @GetMapping("/me")
    public ResponseEntity<UserDto> findCurrentUser() {
        var response = userService.findCurrentUser();
        return ResponseEntity.ok(response);
    }
}
