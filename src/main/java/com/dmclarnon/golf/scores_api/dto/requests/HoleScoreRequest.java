package com.dmclarnon.golf.scores_api.dto.requests;

import com.dmclarnon.golf.scores_api.model.Club;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record HoleScoreRequest(
        @NotNull Integer holeNumber,
        @Min(1) int strokes,
        int putts,
        int penalties,
        List<ShotRequest> shots
) {
    public record ShotRequest(
            int shotNumber,
            Club club
    ) {}
}