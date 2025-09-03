package com.dmclarnon.golf.scores_api.dto.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record HoleScoreRequest(
        @NotNull Integer holeNumber,
        @Min(1) int strokes,
        int putts,
        int penalties
) {}