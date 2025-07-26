package space.pickly.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import space.pickly.domain.review.domain.Review;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDTO {

    private Long id;
    //    private UserDTO reviewAuthor;
    private Long concernId;
    private String reviewContent;

    // Convert Review entity to ReviewDTO
    public static ReviewDTO from(Review review) {
        return ReviewDTO.builder()
                .id(review.getId())
                .concernId(review.getConcern().getId())
                .reviewContent(review.getContent())
                .build();
    }
}
