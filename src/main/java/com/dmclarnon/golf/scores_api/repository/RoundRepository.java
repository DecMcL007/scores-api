package com.dmclarnon.golf.scores_api.repository;

import com.dmclarnon.golf.scores_api.model.Round;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoundRepository extends JpaRepository<Round, Long> {
    List<Round> findByUserId(Long userId);
}
