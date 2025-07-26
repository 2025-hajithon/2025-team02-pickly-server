package space.pickly.domain.article.dao;

import java.util.List;
import space.pickly.domain.article.dto.response.ArticleOngoingResponse;
import space.pickly.domain.article.dto.response.ArticleReviewedResponse;
import space.pickly.domain.article.dto.response.ArticleToReviewResponse;

public interface ArticleCustomRepository {

    List<ArticleOngoingResponse> findOngoingArticles();

    List<ArticleOngoingResponse> findMyOngoingArticles(Long currentUserId);

    List<ArticleReviewedResponse> findReviewedArticles(Long currentUserId);

    List<ArticleToReviewResponse> findMyArticlesToReview(Long currentUserId);

    List<ArticleReviewedResponse> findMyReviewedArticles(Long currentUserId);
}
