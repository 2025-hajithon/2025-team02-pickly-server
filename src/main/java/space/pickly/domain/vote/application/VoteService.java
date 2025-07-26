package space.pickly.domain.vote.application;

import static space.pickly.global.exception.ErrorCode.*;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.pickly.domain.article.dao.ArticleRepository;
import space.pickly.domain.article.domain.Article;
import space.pickly.domain.user.domain.User;
import space.pickly.domain.vote.dao.VoteRepository;
import space.pickly.domain.vote.domain.Vote;
import space.pickly.domain.vote.dto.request.VoteCreateRequest;
import space.pickly.global.exception.CustomException;
import space.pickly.global.util.UserUtil;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final ArticleRepository articleRepository;
    private final UserUtil userUtil;

    @Transactional
    public void createVote(VoteCreateRequest request) {
        User currentUser = userUtil.getCurrentUser();
        Article article = articleRepository
                .findById(request.articleId())
                .orElseThrow(() -> CustomException.from(ARTICLE_NOT_FOUND));
        boolean hasVoted = voteRepository.existsByArticleAndUser(article, currentUser);
        LocalDateTime now = LocalDateTime.now();

        if (article.hasVoteEnded(now)) {
            throw CustomException.from(VOTE_NOT_CREATABLE_VOTE_ENDED);
        }

        if (hasVoted) {
            throw CustomException.from(VOTE_NOT_CREATABLE_ALREADY_VOTED);
        }

        Vote vote = Vote.create(request.choice(), article, currentUser);
        voteRepository.save(vote);

        log.info(
                "[VoteService] 투표 생성 완료: articleId={}, userId={}, choice={}",
                article.getId(),
                currentUser.getId(),
                request.choice());
    }
}
