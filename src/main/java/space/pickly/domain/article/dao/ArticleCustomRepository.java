package space.pickly.domain.article.dao;

import java.util.List;
import space.pickly.domain.article.dto.response.ArticleOngoingResponse;
import space.pickly.domain.article.dto.response.ArticleReviewedResponse;

public interface ArticleCustomRepository {

    List<ArticleOngoingResponse> findOngoingArticles();

    List<ArticleReviewedResponse> findReviewedArticles(Long currentUserId);
}
