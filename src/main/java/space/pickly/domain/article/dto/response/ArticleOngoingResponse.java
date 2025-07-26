package space.pickly.domain.article.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import space.pickly.domain.article.domain.Article;
import space.pickly.domain.article.dto.dto.ArticleDto;
import space.pickly.domain.user.domain.User;
import space.pickly.domain.user.dto.dto.UserSimpleDto;

@QueryProjection
public record ArticleOngoingResponse(ArticleDto article, UserSimpleDto user, long voteCount) {
    public static ArticleOngoingResponse from(Article article, User user, long voteCount) {
        return new ArticleOngoingResponse(ArticleDto.from(article), UserSimpleDto.from(user), voteCount);
    }
}
