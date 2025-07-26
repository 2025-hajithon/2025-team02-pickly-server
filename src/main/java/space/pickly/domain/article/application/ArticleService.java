package space.pickly.domain.article.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.pickly.domain.article.dao.ArticleRepository;
import space.pickly.domain.article.domain.Article;
import space.pickly.domain.article.dto.request.ArticleCreateRequest;
import space.pickly.domain.article.dto.response.ArticleOngoingResponse;
import space.pickly.domain.article.dto.response.ArticleReviewedResponse;
import space.pickly.domain.user.domain.User;
import space.pickly.global.util.UserUtil;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserUtil userUtil;

    @Transactional
    public void createArticle(ArticleCreateRequest request) {
        User currentUser = userUtil.getCurrentUser();

        Article article = Article.create(
                request.title(),
                request.content(),
                request.firstChoice(),
                request.secondChoice(),
                request.voteEndsAt(),
                currentUser);

        articleRepository.save(article);

        log.info("[ArticleService] 고민 생성 완료: articleId={}, userId={}", article.getId(), currentUser.getId());
    }

    @Transactional(readOnly = true)
    public List<ArticleOngoingResponse> findOngoingArticles() {
        return articleRepository.findOngoingArticles();
    }

    @Transactional(readOnly = true)
    public List<ArticleReviewedResponse> findReviewedArticles() {
        User currentUser = userUtil.getCurrentUser();
        return articleRepository.findReviewedArticles(currentUser.getId());
    }
}
