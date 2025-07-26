package space.pickly.domain.article.dao;

import java.util.List;
import space.pickly.domain.article.dto.response.ArticleOngoingResponse;

public interface ArticleCustomRepository {

    List<ArticleOngoingResponse> findOngoingArticles();
}
