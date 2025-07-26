package space.pickly.domain.article.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import space.pickly.domain.common.model.BaseEntity;
import space.pickly.domain.user.domain.User;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String firstChoice;

    private String secondChoice;

    private LocalDateTime voteEndsAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    private Article(
            String title,
            String content,
            String firstChoice,
            String secondChoice,
            LocalDateTime voteEndsAt,
            User user) {
        this.title = title;
        this.content = content;
        this.firstChoice = firstChoice;
        this.secondChoice = secondChoice;
        this.voteEndsAt = voteEndsAt;
        this.user = user;
    }

    public static Article create(
            String title,
            String content,
            String firstChoice,
            String secondChoice,
            LocalDateTime voteEndsAt,
            User user) {
        return Article.builder()
                .title(title)
                .content(content)
                .firstChoice(firstChoice)
                .secondChoice(secondChoice)
                .voteEndsAt(voteEndsAt)
                .user(user)
                .build();
    }
}
