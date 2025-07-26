package space.pickly.domain.reaction.application;

import static space.pickly.global.exception.ErrorCode.*;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.pickly.domain.article.dao.ArticleRepository;
import space.pickly.domain.article.domain.Article;
import space.pickly.domain.reaction.dao.ReactionRepository;
import space.pickly.domain.reaction.domain.Reaction;
import space.pickly.domain.reaction.dto.request.ReactionCreateOrUpdateRequest;
import space.pickly.domain.user.domain.User;
import space.pickly.global.exception.CustomException;
import space.pickly.global.util.UserUtil;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReactionService {

    private final UserUtil userUtil;
    private final ReactionRepository reactionRepository;
    private final ArticleRepository articleRepository;

    @Transactional
    public void createOrUpdateReaction(ReactionCreateOrUpdateRequest request) {
        User currentUser = userUtil.getCurrentUser();
        Article article = articleRepository
                .findById(request.articleId())
                .orElseThrow(() -> CustomException.from(ARTICLE_NOT_FOUND));

        boolean isAuthor = article.getUser().getId().equals(currentUser.getId());
        if (isAuthor) {
            throw CustomException.from(REACTION_NOT_CREATABLE_AUTHOR);
        }

        Optional<Reaction> optionalReaction = reactionRepository.findByArticleAndUser(article, currentUser);

        if (optionalReaction.isPresent()) {
            Reaction reaction = optionalReaction.get();
            reaction.updateType(request.type());
        } else {
            Reaction reaction = Reaction.create(request.type(), article, currentUser);
            reactionRepository.save(reaction);
        }

        log.info(
                "[ReactionService] 리액션 생성 혹은 수정 완료: articleId={}, userId={}, type={}",
                article.getId(),
                currentUser.getId(),
                request.type());
    }

    @Transactional
    public void deleteReaction(Long articleId) {
        User currentUser = userUtil.getCurrentUser();
        Article article =
                articleRepository.findById(articleId).orElseThrow(() -> CustomException.from(ARTICLE_NOT_FOUND));

        Reaction reaction = reactionRepository
                .findByArticleAndUser(article, currentUser)
                .orElseThrow(() -> CustomException.from(REACTION_NOT_FOUND));

        reactionRepository.delete(reaction);

        log.info("[ReactionService] 리액션 삭제 완료: articleId={}, userId={}", article.getId(), currentUser.getId());
    }
}
