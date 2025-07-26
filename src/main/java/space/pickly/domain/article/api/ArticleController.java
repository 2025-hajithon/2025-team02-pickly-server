package space.pickly.domain.article.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import space.pickly.domain.article.application.ArticleService;
import space.pickly.domain.article.dto.request.ArticleCreateRequest;

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
}
