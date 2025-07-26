package space.pickly.domain.review.api;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import space.pickly.domain.review.application.ReviewService;
import space.pickly.domain.review.dto.ConcernReviewCardDTO;
import space.pickly.domain.review.dto.ReviewCreateRequestDTO;
import space.pickly.domain.review.dto.ReviewDTO;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * Create a new review
     * 고민 작성자는 후기를 작성 가능
     */
    @PostMapping
    public ResponseEntity<ReviewDTO> createReview(
            @RequestBody ReviewCreateRequestDTO requestDTO, @AuthenticationPrincipal Long tempUserId) {

        ReviewDTO reviewDTO = reviewService.createReview(requestDTO, 1L); // Using hardcoded user ID for now

        return ResponseEntity.ok(reviewDTO);
    }

    /**
     * Get all reviews
     * 전체 고민별 후기 조회
     */
    @GetMapping
    public ResponseEntity<List<ConcernReviewCardDTO>> getAllReviewsWithConcern() {

        List<ConcernReviewCardDTO> reviews = reviewService.getAllReviews();

        return ResponseEntity.ok(reviews);
    }
}
