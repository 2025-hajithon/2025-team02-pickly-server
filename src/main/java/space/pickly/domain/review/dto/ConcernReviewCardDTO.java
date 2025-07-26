package space.pickly.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import space.pickly.domain.concern.dto.ConcernFeedDTO;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConcernReviewCardDTO {
    private ConcernFeedDTO concern;
    private boolean hasReviewed;
    private ReviewDTO review;

    public static ConcernReviewCardDTO from(ConcernFeedDTO concern, boolean hasReviewed, ReviewDTO review) {
        return ConcernReviewCardDTO.builder()
                .concern(concern)
                .hasReviewed(hasReviewed)
                .review(review)
                .build();
    }
}
