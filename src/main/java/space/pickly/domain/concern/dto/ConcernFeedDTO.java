package space.pickly.domain.concern.dto;

import lombok.*;
import space.pickly.domain.concern.domain.Concern;
import space.pickly.domain.review.dto.ConcernReviewCardDTO;
import space.pickly.domain.vote.dto.VoteDTO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 홈 화면 – 고민(Concern) 피드 한 건
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ConcernFeedDTO {
    private Long id;                     // 고민 ID
    private String title;                // 고민 제목
    private String contentSnippet;       // 화면에 보여줄 간략 본문 (ex: 2줄 이내)
    private int totalParticipants;       // “20명이 골라줬어요”
    private List<ConcernItemFeedDTO> items;     // 선택지 (화면엔 2개 버튼)
//    private MemberDto author;            // 작성자 정보 (닉네임 + 프로필)
//    private long remainingMinutes;       // 남은 투표 시간 (분 단위) => client 직접계산
    private VoteDTO vote;

    public static ConcernFeedDTO from(Concern concern, List<ConcernItemFeedDTO> items) {
        return ConcernFeedDTO.builder()
                .id(concern.getId())
                .title(concern.getTitle())
                .contentSnippet(concern.getContent())
                .totalParticipants(concern.getTotalParticipants())
                .items(items)
                .build();
    }
}