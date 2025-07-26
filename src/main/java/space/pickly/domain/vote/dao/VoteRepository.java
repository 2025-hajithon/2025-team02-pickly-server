package space.pickly.domain.vote.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import space.pickly.domain.article.domain.Article;
import space.pickly.domain.user.domain.User;
import space.pickly.domain.vote.domain.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    boolean existsByArticleAndUser(Article article, User user);
}
