package space.pickly.domain.reaction.dto.request;

import space.pickly.domain.reaction.domain.ReactionType;

public record ReactionCreateOrUpdateRequest(Long articleId, ReactionType type) {}
