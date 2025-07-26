package space.pickly.domain.reaction.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import space.pickly.domain.reaction.application.ReactionService;
import space.pickly.domain.reaction.dto.request.ReactionCreateOrUpdateRequest;

@Tag(name = "[Reaction]", description = "리액션 API")
@RestController
@RequestMapping("/reactions")
@RequiredArgsConstructor
public class ReactionController {

    private final ReactionService reactionService;

    @Operation(summary = "리액션 생성 및 수정", description = "게시글에 리액션을 생성하거나 변경합니다.")
    @PutMapping
    public ResponseEntity<Void> createOrUpdateReaction(@RequestBody ReactionCreateOrUpdateRequest request) {
        reactionService.createOrUpdateReaction(request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "리액션 삭제", description = "게시글에 대한 리액션을 삭제합니다.")
    @DeleteMapping
    public ResponseEntity<Void> deleteReaction(@RequestParam(name = "article") Long articleId) {
        reactionService.deleteReaction(articleId);
        return ResponseEntity.ok().build();
    }
}
