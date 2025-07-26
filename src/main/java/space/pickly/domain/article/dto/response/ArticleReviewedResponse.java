package space.pickly.domain.article.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import java.util.Map;
import space.pickly.domain.article.dto.dto.ArticleDto;
import space.pickly.domain.reaction.domain.ReactionType;
import space.pickly.domain.user.dto.dto.UserSimpleDto;

@QueryProjection
public record ArticleReviewedResponse(
        ArticleDto article,
        UserSimpleDto user,
        int firstChoicePercentage,
        int secondChoicePercentage,
        Map<ReactionType, Integer> reactionCountByType,
        int totalReactionCount) {}
