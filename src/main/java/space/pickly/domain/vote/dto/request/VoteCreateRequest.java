package space.pickly.domain.vote.dto.request;

import space.pickly.domain.article.domain.Choice;

public record VoteCreateRequest(Long articleId, Choice choice) {}
