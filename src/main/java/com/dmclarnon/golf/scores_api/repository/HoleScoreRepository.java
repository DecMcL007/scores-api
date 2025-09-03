package com.dmclarnon.golf.scores_api.repository;

import com.dmclarnon.golf.scores_api.model.HoleScore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HoleScoreRepository extends JpaRepository<HoleScore, Long> {
    List<HoleScore> findByRoundId(Long roundId);
}