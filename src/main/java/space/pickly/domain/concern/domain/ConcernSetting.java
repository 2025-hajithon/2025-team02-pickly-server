package space.pickly.domain.concern.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "concern_settings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConcernSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 어느 고민(Concern)에 대한 설정인지 **/
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concern_id", nullable = false)
    private Concern concern;

    /** 투표 지속 시간 (분 단위) **/
    private int durationMinutes;

    /** 동률 시 보여줄 대체 문구 등 **/
    private String description;
}
