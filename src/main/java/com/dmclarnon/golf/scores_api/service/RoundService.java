package com.dmclarnon.golf.scores_api.service;

import com.dmclarnon.golf.scores_api.dto.requests.CreateRoundRequest;
import com.dmclarnon.golf.scores_api.dto.requests.HoleScoreRequest;
import com.dmclarnon.golf.scores_api.dto.responses.RoundSummaryResponse;
import com.dmclarnon.golf.scores_api.model.HoleScore;
import com.dmclarnon.golf.scores_api.model.Round;
import com.dmclarnon.golf.scores_api.model.RoundStatus;
import com.dmclarnon.golf.scores_api.repository.HoleScoreRepository;
import com.dmclarnon.golf.scores_api.repository.RoundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class RoundService {

    private final RoundRepository roundRepository;
    private final HoleScoreRepository holeScoreRepository;

    // For future use — fetch from login-api via RestTemplate/WebClient
    private double fetchUserHandicap(Long userId) {
        return 18.0; // stubbed
    }

    public Long startRound(CreateRoundRequest request) {
        Round round = new Round();
        round.setUserId(request.userId());
        round.setCourseId(request.courseId());
        round.setStartedAt(LocalDateTime.now());
        round.setStatus(RoundStatus.IN_PROGRESS);
        round.setHandicapBefore(fetchUserHandicap(request.userId()));

        return roundRepository.save(round).getId();
    }

    public void addHoleScore(Long roundId, HoleScoreRequest req) {
        Round round = roundRepository.findById(roundId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Round not found"));

        if (round.getStatus() == RoundStatus.COMPLETED) {
            throw new IllegalStateException("Cannot add scores to a completed round.");
        }

        HoleScore score = new HoleScore();
        score.setHoleNumber(req.holeNumber());
        score.setStrokes(req.strokes());
        score.setPutts(req.putts());
        score.setPenalties(req.penalties());
        score.setRecordedAt(LocalDateTime.now());
        score.setRound(round);

        holeScoreRepository.save(score);
    }

    public RoundSummaryResponse completeRound(Long roundId) {
        Round round = roundRepository.findById(roundId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Round not found"));

        round.setCompletedAt(LocalDateTime.now());
        round.setStatus(RoundStatus.COMPLETED);

        double newHandicap = recalculateHandicap(round);
        round.setHandicapAfter(newHandicap);

        roundRepository.save(round);

        return buildSummary(round);
    }

    public RoundSummaryResponse getSummary(Long roundId) {
        Round round = roundRepository.findById(roundId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Round not found"));

        return buildSummary(round);
    }

    private RoundSummaryResponse buildSummary(Round round) {
        List<HoleScore> scores = holeScoreRepository.findByRoundId(round.getId());

        List<RoundSummaryResponse.HoleScoreEntry> entries = scores.stream()
                .map(s -> new RoundSummaryResponse.HoleScoreEntry(
                        s.getHoleNumber(),
                        s.getStrokes(),
                        s.getPutts(),
                        s.getPenalties()))
                .toList();

        return new RoundSummaryResponse(
                round.getId(),
                round.getUserId(),
                round.getCourseId(),
                round.getHandicapBefore(),
                round.getHandicapAfter(),
                entries
        );
    }

    private double recalculateHandicap(Round round) {
        // TODO: Replace with real logic later
        return round.getHandicapBefore() - 0.1;
    }
}