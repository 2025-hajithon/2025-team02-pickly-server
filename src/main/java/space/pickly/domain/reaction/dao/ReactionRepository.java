package space.pickly.domain.reaction.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import space.pickly.domain.article.domain.Article;
import space.pickly.domain.reaction.domain.Reaction;
import space.pickly.domain.user.domain.User;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {
    Optional<Reaction> findByArticleAndUser(Article article, User currentUser);
}
