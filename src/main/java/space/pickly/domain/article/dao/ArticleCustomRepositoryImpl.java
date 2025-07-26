package space.pickly.domain.article.dao;

import static space.pickly.domain.article.domain.QArticle.*;
import static space.pickly.domain.user.domain.QUser.*;
import static space.pickly.domain.vote.domain.QVote.*;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import space.pickly.domain.article.dto.dto.QArticleDto;
import space.pickly.domain.article.dto.response.ArticleOngoingResponse;
import space.pickly.domain.article.dto.response.QArticleOngoingResponse;
import space.pickly.domain.user.dto.dto.QUserSimpleDto;

@Repository
@RequiredArgsConstructor
public class ArticleCustomRepositoryImpl implements ArticleCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ArticleOngoingResponse> findOngoingArticles() {
        LocalDateTime now = LocalDateTime.now();

        return queryFactory
                .select(getArticleOngoingResponse())
                .from(article)
                .innerJoin(article.user, user)
                .where(article.voteEndsAt.gt(now))
                .orderBy(article.voteEndsAt.asc())
                .fetch();
    }

    private QArticleOngoingResponse getArticleOngoingResponse() {
        return new QArticleOngoingResponse(getArticleDto(), getUserSimpleDto(), vote.count());
    }

    private QArticleDto getArticleDto() {
        return new QArticleDto(
                article.id,
                article.title,
                article.content,
                article.firstChoice,
                article.secondChoice,
                article.voteEndsAt,
                article.review,
                article.user.id);
    }

    private QUserSimpleDto getUserSimpleDto() {
        return new QUserSimpleDto(user.id, user.nickname, user.profileImageUrl);
    }
}
