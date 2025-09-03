package com.dmclarnon.golf.scores_api.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "rounds")
public class Round {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long courseId;

    private LocalDateTime startedAt;
    private LocalDateTime completedAt;

    private Double handicapBefore;

    private Double handicapAfter;

    @Enumerated(EnumType.STRING)
    private RoundStatus status;

    @OneToMany(mappedBy = "round", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<HoleScore> holeScores = new ArrayList<>();
}
