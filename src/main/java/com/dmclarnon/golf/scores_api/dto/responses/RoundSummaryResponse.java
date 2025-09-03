package com.dmclarnon.golf.scores_api.dto.responses;

import java.util.List;

public record RoundSummaryResponse(
        Long roundId,
        Long userId,
        Long courseId,
        Double handicapBefore,
        Double handicapAfter,
        List<HoleScoreEntry> holeScores
) {
    public record HoleScoreEntry(
            int holeNumber,
            int strokes,
            int putts,
            int penalties
    ) {}
}