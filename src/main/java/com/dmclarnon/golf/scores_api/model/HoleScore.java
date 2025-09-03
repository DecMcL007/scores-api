package com.dmclarnon.golf.scores_api.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "hole_score")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HoleScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int holeNumber;
    @Column(nullable = false)
    private int strokes;
    private int putts;
    private int penalties;

    private LocalDateTime recordedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "round_id", nullable = false)
    private Round round;
}
