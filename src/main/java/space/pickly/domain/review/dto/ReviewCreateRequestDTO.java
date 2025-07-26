package space.pickly.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import space.pickly.domain.concern.domain.Concern;
import space.pickly.domain.concern.domain.ConcernItem;
import space.pickly.domain.review.domain.Review;
import space.pickly.domain.user.domain.User;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewCreateRequestDTO {
    private Long concernId;
    private String content;
    private Long selectedItemId;
    
    // Convert DTO to Review entity
    public Review toReview(Concern concern, User author, ConcernItem selectedItem) {
        return Review.builder()
                .concern(concern)
                .author(author)
                .content(content)
                .createdAt(LocalDateTime.now())
                .likeCount(0)
                .selectedItem(selectedItem)
                .isDeleted(false)
                .build();
    }
}