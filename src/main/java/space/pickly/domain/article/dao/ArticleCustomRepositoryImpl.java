package space.pickly.domain.article.dao;

import static com.querydsl.core.group.GroupBy.*;
import static space.pickly.domain.article.domain.QArticle.*;
import static space.pickly.domain.reaction.domain.QReaction.*;
import static space.pickly.domain.user.domain.QUser.*;
import static space.pickly.domain.vote.domain.QVote.*;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import space.pickly.domain.article.domain.Choice;
import space.pickly.domain.article.dto.dto.QArticleDto;
import space.pickly.domain.article.dto.response.ArticleOngoingResponse;
import space.pickly.domain.article.dto.response.ArticleReviewedResponse;
import space.pickly.domain.article.dto.response.ArticleToReviewResponse;
import space.pickly.domain.article.dto.response.QArticleOngoingResponse;
import space.pickly.domain.article.dto.response.QArticleReviewedResponse;
import space.pickly.domain.article.dto.response.QArticleToReviewResponse;
import space.pickly.domain.reaction.domain.ReactionType;
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

    @Override
    public List<ArticleReviewedResponse> findReviewedArticles(Long currentUserId) {
        LocalDateTime now = LocalDateTime.now();

        List<Long> articleIds = queryFactory
                .select(article.id)
                .from(article)
                .innerJoin(vote)
                .on(vote.article.eq(article).and(vote.user.id.eq(currentUserId)))
                .where(article.voteEndsAt.lt(now).and(article.review.choice.ne(Choice.NONE)))
                .orderBy(article.updatedAt.desc())
                .fetch();

        return articleIds.stream().map(this::buildArticleReviewedResponse).toList();
    }

    @Override
    public List<ArticleToReviewResponse> findMyArticlesToReview(Long currentUserId) {
        LocalDateTime now = LocalDateTime.now();

        List<Long> articleIds = queryFactory
                .select(article.id)
                .from(article)
                .where(article.user
                        .id
                        .eq(currentUserId)
                        .and(article.voteEndsAt.lt(now))
                        .and(article.review.choice.eq(Choice.NONE)))
                .orderBy(article.voteEndsAt.desc())
                .fetch();

        return articleIds.stream().map(this::buildArticleToReviewResponse).toList();
    }

    private ArticleReviewedResponse buildArticleReviewedResponse(Long articleId) {
        Map<ReactionType, Integer> reactionCountMap = queryFactory
                .from(reaction)
                .where(reaction.article.id.eq(articleId))
                .transform(groupBy(reaction.type).as(reaction.count().intValue()));

        int totalReactionCount =
                reactionCountMap.values().stream().mapToInt(Integer::intValue).sum();

        return queryFactory
                .select(new QArticleReviewedResponse(
                        getArticleDto(),
                        getUserSimpleDto(),
                        getFirstChoicePercentage(),
                        getSecondChoicePercentage(),
                        Expressions.constant(reactionCountMap),
                        Expressions.constant(totalReactionCount)))
                .from(article)
                .innerJoin(article.user, user)
                .where(article.id.eq(articleId))
                .fetchOne();
    }

    private NumberExpression<Integer> getFirstChoicePercentage() {
        return Expressions.numberTemplate(
                Integer.class,
                "CAST(COALESCE((SELECT COUNT(*) FROM {0} WHERE {1} = {2} AND {3} = {4}) * 100 / "
                        + "NULLIF((SELECT COUNT(*) FROM {0} WHERE {1} = {2}), 0), 0) AS INTEGER)",
                vote,
                vote.article,
                article,
                vote.choice,
                Choice.FIRST);
    }

    private NumberExpression<Integer> getSecondChoicePercentage() {
        return Expressions.numberTemplate(
                Integer.class,
                "CAST(COALESCE((SELECT COUNT(*) FROM {0} WHERE {1} = {2} AND {3} = {4}) * 100 / "
                        + "NULLIF((SELECT COUNT(*) FROM {0} WHERE {1} = {2}), 0), 0) AS INTEGER)",
                vote,
                vote.article,
                article,
                vote.choice,
                Choice.SECOND);
    }

    private ArticleToReviewResponse buildArticleToReviewResponse(Long articleId) {
        Long voteCount = queryFactory
                .select(vote.count())
                .from(vote)
                .where(vote.article.id.eq(articleId))
                .fetchOne();

        return queryFactory
                .select(new QArticleToReviewResponse(
                        getArticleDto(),
                        getUserSimpleDto(),
                        Expressions.constant(voteCount != null ? voteCount : 0L),
                        getFirstChoicePercentage(),
                        getSecondChoicePercentage()))
                .from(article)
                .innerJoin(article.user, user)
                .where(article.id.eq(articleId))
                .fetchOne();
    }
}
