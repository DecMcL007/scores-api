package com.dmclarnon.golf.scores_api.controller;

import com.dmclarnon.golf.scores_api.dto.requests.CreateRoundRequest;
import com.dmclarnon.golf.scores_api.dto.requests.HoleScoreRequest;
import com.dmclarnon.golf.scores_api.dto.responses.RoundSummaryResponse;
import com.dmclarnon.golf.scores_api.service.RoundService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/scores")
@RequiredArgsConstructor

public class RoundsController {

    private final RoundService roundService;

    //Starting a new round
    @PostMapping("/rounds")
    public ResponseEntity<Long> startRound(@RequestBody @Valid CreateRoundRequest request){
        Long roundId = roundService.startRound(request);
        return ResponseEntity.ok(roundId);
    }

    //Submit a hole score
    @PostMapping("/rounds/{roundId}/holes")
    public ResponseEntity<Void> addHoleScore(
            @PathVariable Long roundId,
            @RequestBody @Valid HoleScoreRequest holeScoreRequest) {

        roundService.addHoleScore(roundId, holeScoreRequest);
        return ResponseEntity.ok().build();
    }

    //Complete the round
    @PostMapping("/rounds/{roundId}/complete")
    public ResponseEntity<RoundSummaryResponse> completeRound(@PathVariable Long roundId){
        RoundSummaryResponse summary = roundService.completeRound(roundId);
        return ResponseEntity.ok(summary);
    }

    //Get a summary for a roundId
    @GetMapping("/rounds/{roundId}/summary")
    public ResponseEntity<RoundSummaryResponse> getSummary(@PathVariable Long roundId){
        return ResponseEntity.ok(roundService.getSummary(roundId));
    }
}
