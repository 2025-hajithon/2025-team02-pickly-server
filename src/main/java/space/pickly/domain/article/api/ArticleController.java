package space.pickly.domain.article.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import space.pickly.domain.article.application.ArticleService;
import space.pickly.domain.article.dto.request.ArticleCreateRequest;
import space.pickly.domain.article.dto.response.ArticleOngoingResponse;
import space.pickly.domain.article.dto.response.ArticleReviewedResponse;
import space.pickly.domain.article.dto.response.ArticleToReviewResponse;

@Tag(name = "[Article]", description = "게시글 API")
@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @Operation(summary = "고민 생성", description = "새로운 고민을 작성합니다.")
    @PostMapping
    public ResponseEntity<Void> createArticle(@RequestBody ArticleCreateRequest request) {
        articleService.createArticle(request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "진행 중인 고민 목록 조회", description = "진행 중인 고민 목록을 모두 조회합니다.")
    @GetMapping("/ongoing")
    public ResponseEntity<List<ArticleOngoingResponse>> findOngoingArticles() {
        var response = articleService.findOngoingArticles();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "진행 중인 내 고민 목록 조회", description = "진행 중인 내 고민 목록을 모두 조회합니다.")
    @GetMapping("/ongoing/me")
    public ResponseEntity<List<ArticleOngoingResponse>> findMyOngoingArticles() {
        var response = articleService.findMyOngoingArticles();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "투표 후 후기 작성된 고민 목록 조회", description = "내가 투표한 이후 후기가 작성된 고민 목록을 모두 조회합니다.")
    @GetMapping("/reviewed")
    public ResponseEntity<List<ArticleReviewedResponse>> findReviewedArticles() {
        var response = articleService.findReviewedArticles();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "투표 후 후기 작성된 내 고민 목록 조회", description = "후기가 작성된 내 고민 목록을 모두 조회합니다.")
    @GetMapping("/reviewed/me")
    public ResponseEntity<List<ArticleReviewedResponse>> findMyReviewedArticles() {
        var response = articleService.findMyReviewedArticles();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "종료 후 후기 작성 필요한 내 고민 목록 조회", description = "종료된 고민 중 내가 작성하지 않은 후기 목록을 모두 조회합니다.")
    @GetMapping("/to-review")
    public ResponseEntity<List<ArticleToReviewResponse>> findMyArticlesToReview() {
        var response = articleService.findMyArticlesToReview();
        return ResponseEntity.ok(response);
    }
}
