package space.pickly.domain.article.dto.dto;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import space.pickly.domain.article.domain.Article;
import space.pickly.domain.article.domain.Review;

@QueryProjection
public record ArticleDto(
        Long articleId,
        String title,
        String content,
        String firstChoice,
        String secondChoice,
        LocalDateTime voteEndsAt,
        Review review,
        Long userId) {
    public static ArticleDto from(Article article) {
        return new ArticleDto(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                article.getFirstChoice(),
                article.getSecondChoice(),
                article.getVoteEndsAt(),
                article.getReview(),
                article.getUser().getId());
    }
}
