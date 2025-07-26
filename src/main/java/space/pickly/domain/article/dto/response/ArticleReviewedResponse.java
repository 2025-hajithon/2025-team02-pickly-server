package space.pickly.domain.article.dto.response;

import space.pickly.domain.article.dto.dto.ArticleDto;
import space.pickly.domain.user.dto.dto.UserSimpleDto;

public record ArticleReviewedResponse(
        ArticleDto article,
        UserSimpleDto user,
        int firstChoicePercentage,
        int secondChoicePercentage,
        int allreactionCount,
        int firstReactionCount,
        int secondReactionCount,
        int thirdReactionCount,
        int fourthReactionCount) {}
