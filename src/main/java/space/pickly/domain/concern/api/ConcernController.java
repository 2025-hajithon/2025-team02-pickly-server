package space.pickly.domain.concern.api;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import space.pickly.domain.concern.application.ConcernService;
import space.pickly.domain.concern.domain.Concern;
import space.pickly.domain.concern.dto.ConcernCreateRequestDTO;
import space.pickly.domain.concern.dto.ConcernFeedDTO;

@RestController
@RequestMapping("/api/concerns")
@RequiredArgsConstructor
public class ConcernController {

    private final ConcernService concernService;

    /**
     * Create a new concern
     * 고민 작성
     */
    @PostMapping
    public ResponseEntity<ConcernFeedDTO> createConcern(
            @RequestBody ConcernCreateRequestDTO requestDTO, @AuthenticationPrincipal Long tempUserId) {

        Concern concern = requestDTO.toConcern(1L);
        ConcernFeedDTO dto = concernService.createConcern(concern);

        return ResponseEntity.ok(dto);
    }

    /**
     * Get all concerns by the current user
     * 내 고민 조회 - all
     */
    @GetMapping("/my")
    public ResponseEntity<List<ConcernFeedDTO>> getMyConcerns(@AuthenticationPrincipal Long tempUserId) {

        List<ConcernFeedDTO> concerns = concernService.getMyConcerns(1L);

        return ResponseEntity.ok(concerns);
    }

    /**
     * Get concerns by the current user where review is null
     * 내 고민 조회 - 후기 작성 안한 고민
     */
    @GetMapping("/my/without-review")
    public ResponseEntity<List<ConcernFeedDTO>> getMyConcernsWithoutReview(@AuthenticationPrincipal Long tempUserId) {

        List<ConcernFeedDTO> concerns = concernService.getMyConcernsWithoutReview(1L);

        return ResponseEntity.ok(concerns);
    }

    /**
     * Get all concerns with OPEN status
     * 전체 고민 조회 - 현재 투표 진행중인 고민만
     */
    @GetMapping("/ongoing")
    public ResponseEntity<List<ConcernFeedDTO>> getOngoingConcerns() {

        List<ConcernFeedDTO> concerns = concernService.getOngoingConcerns();

        return ResponseEntity.ok(concerns);
    }
}
