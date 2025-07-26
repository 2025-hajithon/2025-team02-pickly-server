package space.pickly.domain.concern.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import space.pickly.domain.concern.domain.Concern;
import space.pickly.domain.concern.domain.ConcernItem;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConcernCreateRequestDTO {
    private String title;
    private String content;
    private List<String> itemContents; // List of item contents
    private LocalDateTime expiresAt;
    private String category;

    /**
     * Convert DTO to Concern entity
     */
    public Concern toConcern(Long authorId) {
        Concern concern = Concern.builder()
                .title(title)
                .content(content)
                .authorId(authorId)
                .expiresAt(expiresAt)
                .totalParticipants(0)
                .build();

        // Create and add items
        if (itemContents != null) {
            List<ConcernItem> items = itemContents.stream()
                    .map(itemContent -> ConcernItem.builder()
                            .content(itemContent)
                            .voteCount(0)
                            .isWinning(false)
                            .concern(concern)
                            .build())
                    .collect(Collectors.toList());

            concern.setItems(items);
        }

        return concern;
    }
}
