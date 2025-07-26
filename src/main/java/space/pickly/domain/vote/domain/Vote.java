package space.pickly.domain.vote.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import space.pickly.domain.article.domain.Article;
import space.pickly.domain.article.domain.Choice;
import space.pickly.domain.common.model.BaseEntity;
import space.pickly.domain.user.domain.User;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "vote",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"article_id", "user_id"})})
public class Vote extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vote_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Choice choice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder(access = AccessLevel.PRIVATE)
    private Vote(Choice choice, Article article, User user) {
        this.choice = choice;
        this.article = article;
        this.user = user;
    }

    public static Vote create(Choice choice, Article article, User user) {
        return Vote.builder().choice(choice).article(article).user(user).build();
    }
}
