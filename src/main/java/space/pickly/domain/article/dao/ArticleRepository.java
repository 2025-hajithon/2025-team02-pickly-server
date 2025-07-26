package space.pickly.domain.article.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import space.pickly.domain.article.domain.Article;

public interface ArticleRepository extends JpaRepository<Article, Long>, ArticleCustomRepository {}
