package space.pickly.domain.concern.dto;

import lombok.*;
import space.pickly.domain.concern.domain.ConcernItem;

/**
 * 홈 화면 – 고민 아이템(선택지) 최소 정보
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConcernItemFeedDTO {
    private Long id;
    private String content;
    private int voteCount;    // 투표 수
    private double percent;

    public static ConcernItemFeedDTO from(ConcernItem concernItem, int totalVoteCount) {
        return ConcernItemFeedDTO.builder()
                .id(concernItem.getId())
                .content(concernItem.getContent())
                .voteCount(concernItem.getVoteCount())
                .percent(((double) concernItem.getVoteCount()) / totalVoteCount)
                .build();

    }
}
