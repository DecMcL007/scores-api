package com.dmclarnon.golf.scores_api.dto.requests;

import jakarta.validation.constraints.NotNull;

public record CreateRoundRequest(
        @NotNull Long userId,
        @NotNull Long courseId
) {}
