package space.pickly.domain.review.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.pickly.domain.concern.application.ConcernConverter;
import space.pickly.domain.concern.dao.ConcernRepository;
import space.pickly.domain.concern.domain.Concern;
import space.pickly.domain.concern.domain.ConcernItem;
import space.pickly.domain.concern.dto.ConcernFeedDTO;
import space.pickly.domain.review.dao.ReviewRepository;
import space.pickly.domain.review.domain.Review;
import space.pickly.domain.review.dto.ConcernReviewCardDTO;
import space.pickly.domain.review.dto.ReviewDTO;
import space.pickly.domain.review.dto.ReviewCreateRequestDTO;
import space.pickly.domain.user.dao.UserRepository;
import space.pickly.domain.user.domain.User;
import space.pickly.global.exception.CustomException;
import space.pickly.global.exception.ErrorCode;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ConcernRepository concernRepository;
    private final UserRepository userRepository;

    /**
     * Create a new review
     * Only the author of the concern can create a review
     */
    @Transactional
    public ReviewDTO createReview(ReviewCreateRequestDTO requestDTO, Long userId) {
        // Find the concern
        Concern concern = concernRepository.findById(requestDTO.getConcernId())
                .orElseThrow(() -> CustomException.from(ErrorCode.CONCERN_NOT_FOUND));

        // Check if the user is the author of the concern
        if (!concern.getAuthorId().equals(userId)) {
            throw CustomException.from(ErrorCode.UNAUTHORIZED_ACCESS);
        }

        // Check if the concern already has a review
        if (concern.getReview() != null) {
            throw CustomException.from((ErrorCode.REVIEW_ALREADY_EXISTS));
        }

        // Find the user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> CustomException.from((ErrorCode.USER_NOT_FOUND)));

        // Find the selected item
        ConcernItem selectedItem = concern.getItems().stream()
                .filter(item -> item.getId().equals(requestDTO.getSelectedItemId()))
                .findFirst()
                .orElseThrow(() -> CustomException.from((ErrorCode.CONCERN_ITEM_NOT_FOUND)));

        // Create and save the review
        Review review = requestDTO.toReview(concern, user, selectedItem);
        Review savedReview = reviewRepository.save(review);

        return ReviewDTO.from(savedReview);
    }


    /**
     * Get all reviews
     */
    public List<ConcernReviewCardDTO> getAllReviews() {
        // 1) 먼저, 전체 고민 피드용 DTO 리스트를 가져온다.
        List<ConcernFeedDTO> feeds = concernRepository.findAll().stream()
                .map(ConcernConverter::mapToConcernFeedDTO)
                .toList();

        // 2) 각 피드에 리뷰가 있는지 조회해서 ConcernReviewCardDTO 로 변환
        return feeds.stream()
                .map(feed -> {
                    // (a) 해당 고민에 대한 Review 엔티티 조회
                    Optional<Review> optReview = reviewRepository.findByConcern_Id((feed.getId()));

                    // (b) 존재 여부로 hasReviewed 판단
                    boolean hasReviewed = optReview.isPresent();

                    // (c) ReviewDTO 로 매핑 (없으면 null)
                    ReviewDTO reviewDto = optReview
                            .map(ReviewDTO::from)
                            .orElse(null);

                    // (d) 최종 ConcermReviewCardDTO 생성
                    return ConcernReviewCardDTO.from(feed, hasReviewed, reviewDto);
                })
                .collect(Collectors.toList());
    }
}
