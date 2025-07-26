package space.pickly.domain.vote.dto;
// --- 1. VoteRequestDto ------------------------------------------

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class VoteCreateDTO {
    private Long itemId;
    private Long concernId;
}

