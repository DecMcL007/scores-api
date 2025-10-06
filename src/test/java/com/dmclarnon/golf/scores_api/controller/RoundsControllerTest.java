package com.dmclarnon.golf.scores_api.controller;

import com.dmclarnon.golf.scores_api.dto.requests.CreateRoundRequest;
import com.dmclarnon.golf.scores_api.dto.requests.HoleScoreRequest;
import com.dmclarnon.golf.scores_api.dto.responses.RoundSummaryResponse;
import com.dmclarnon.golf.scores_api.service.RoundService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RoundsController.class)
class RoundsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RoundService roundService;

    private RoundSummaryResponse summary;

    @BeforeEach
    void setUp() {
        summary = new RoundSummaryResponse(
                1L,       // roundId
                10L,      // userId
                20L,      // courseId
                15.2,     // handicapBefore
                15.0,     // handicapAfter
                List.of(
                        new RoundSummaryResponse.HoleScoreEntry(1, 4, 2, 0),
                        new RoundSummaryResponse.HoleScoreEntry(2, 5, 2, 1)
                )
        );
    }

    @Test
    void startRound_returnsRoundId() throws Exception {
        given(roundService.startRound(any(CreateRoundRequest.class))).willReturn(1L);

        mockMvc.perform(post("/scores/rounds")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"userId":10,"courseId":20}
                                """))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        verify(roundService).startRound(any(CreateRoundRequest.class));
    }

    @Test
    void addHoleScore_returnsOk() throws Exception {
        mockMvc.perform(post("/scores/rounds/1/holes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"holeNumber":1,"strokes":4,"putts":2,"penalties":0,"shots":[]}
                                """))
                .andExpect(status().isOk());

        verify(roundService).addHoleScore(eq(1L), any(HoleScoreRequest.class));
    }

    @Test
    void completeRound_returnsSummary() throws Exception {
        given(roundService.completeRound(1L)).willReturn(summary);

        mockMvc.perform(post("/scores/rounds/1/complete"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roundId").value(1))
                .andExpect(jsonPath("$.userId").value(10))
                .andExpect(jsonPath("$.courseId").value(20))
                .andExpect(jsonPath("$.handicapBefore").value(15.2))
                .andExpect(jsonPath("$.handicapAfter").value(15.0))
                .andExpect(jsonPath("$.holeScores[0].holeNumber").value(1))
                .andExpect(jsonPath("$.holeScores[1].strokes").value(5));

        verify(roundService).completeRound(1L);
    }

    @Test
    void getSummary_returnsSummary() throws Exception {
        given(roundService.getSummary(1L)).willReturn(summary);

        mockMvc.perform(get("/scores/rounds/1/summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roundId").value(1))
                .andExpect(jsonPath("$.holeScores[0].holeNumber").value(1));

        verify(roundService).getSummary(1L);
    }

    @Test
    void startRound_validationError_returnsBadRequest() throws Exception {
        // Missing courseId
        mockMvc.perform(post("/scores/rounds")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"userId":10}
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addHoleScore_validationError_returnsBadRequest() throws Exception {
        // strokes < 1 (violates @Min(1))
        mockMvc.perform(post("/scores/rounds/1/holes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"holeNumber":1,"strokes":0,"putts":2,"penalties":0,"shots":[]}
                                """))
                .andExpect(status().isBadRequest());
    }
}