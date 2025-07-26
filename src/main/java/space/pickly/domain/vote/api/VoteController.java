package space.pickly.domain.vote.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import space.pickly.domain.vote.application.VoteService;
import space.pickly.domain.vote.dto.request.VoteCreateRequest;

@Tag(name = "[Article]", description = "게시글 API")
@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class VoteController {

    private final VoteService voteService;

    @Operation(summary = "투표 생성", description = "특정 고민에 투표합니다.")
    @PostMapping
    public ResponseEntity<Void> createVote(VoteCreateRequest request) {
        voteService.createVote(request);
        return ResponseEntity.ok().build();
    }
}
