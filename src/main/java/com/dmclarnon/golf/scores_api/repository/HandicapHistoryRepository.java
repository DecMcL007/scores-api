package com.dmclarnon.golf.scores_api.repository;

import com.dmclarnon.golf.scores_api.model.HandicapHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HandicapHistoryRepository extends JpaRepository<HandicapHistory, Long> {
    List<HandicapHistory> findByUserIdOrderByEffectiveDateDesc(Long userId);
}
