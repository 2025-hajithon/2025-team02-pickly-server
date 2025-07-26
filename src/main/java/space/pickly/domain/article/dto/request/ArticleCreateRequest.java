package space.pickly.domain.article.dto.request;

import java.time.LocalDateTime;

public record ArticleCreateRequest(
        String title, String content, String firstChoice, String secondChoice, LocalDateTime voteEndsAt) {}
