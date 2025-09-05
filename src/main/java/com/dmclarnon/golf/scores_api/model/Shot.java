package com.dmclarnon.golf.scores_api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "shots")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int shotNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Club club;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hole_score_id", nullable = false)
    private HoleScore holeScore;
}