package space.pickly.domain.article.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public record Review(@Enumerated(EnumType.STRING) Choice choice, String content) {

    public static Review empty() {
        return new Review(Choice.NONE, null);
    }

    public static Review of(Choice choice, String content) {
        return new Review(choice, content);
    }
}
