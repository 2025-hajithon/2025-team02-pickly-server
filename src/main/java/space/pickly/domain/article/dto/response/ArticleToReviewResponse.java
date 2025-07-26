package space.pickly.domain.article.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import space.pickly.domain.article.dto.dto.ArticleDto;
import space.pickly.domain.user.dto.dto.UserSimpleDto;

@QueryProjection
public record ArticleToReviewResponse(
        ArticleDto article,
        UserSimpleDto user,
        long voteCount,
        int firstChoicePercentage,
        int secondChoicePercentage) {}
