package com.bugval.operations;

import com.bugval.domain.Player;
import com.bugval.domain.Score;
import com.bugval.services.PlayerFetcher;
import com.bugval.services.ScoreFetcher;
import com.bugval.services.ScoreRepo;

import javax.inject.Inject;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.joining;

/**
 * Operation invoked to compare the current state of the scores with the last seen state.
 */
public class IterationOperation {
  private static final int NUM_OF_INTEREST = 6;

  private final ScoreRepo scoreRepo;
  private final ScoreFetcher scoreFetcher;
  private final PlayerFetcher playerFetcher;

  @Inject
  public IterationOperation(
      ScoreRepo scoreRepo, ScoreFetcher scoreFetcher, PlayerFetcher playerFetcher) {
    this.scoreRepo = scoreRepo;
    this.scoreFetcher = scoreFetcher;
    this.playerFetcher = playerFetcher;
  }

  public void run() {
    List<Score> newScores = scoreFetcher.fetchScores();
    System.out.println(
        scoreRepo.getScores().isEmpty()
            ? processFirstIteration(newScores)
            : processIteration(newScores));
    System.out.println();
    scoreRepo.saveScores(newScores);
  }

  private String processIteration(List<Score> scores) {
    return scores.stream()
        .sorted(Comparator.comparing(Score::getPosition))
        .limit(NUM_OF_INTEREST)
        .map(
            s -> {
              Player player = playerFetcher.fetchPlayer(s.getId());
              if (!scoreRepo.getScoresByPlayer().containsKey(s.getId())) {
                return Optional.of(
                    String.format(
                        "New Player %s moved into position %d with %d points",
                        player.getName(), s.getPosition(), s.getScore()));
              } else if (scoreRepo.getScoresByPlayer().get(s.getId()).getPosition()
                  > s.getPosition()) {
                return Optional.of(
                    String.format(
                        "Player %s moved up to position %d with %d points",
                        player.getName(), s.getPosition(), s.getScore()));
              } else if (scoreRepo.getScoresByPlayer().get(s.getId()).getPosition()
                  < s.getPosition()) {
                return Optional.of(
                    String.format(
                        "Player %s moved down to position %d with %d points",
                        player.getName(), s.getPosition(), s.getScore()));
              }
              return Optional.<String>empty();
            })
        .flatMap(Optional::stream)
        .collect(joining("\n"));
  }

  private String processFirstIteration(List<Score> scores) {
    return scores.stream()
        .sorted(Comparator.comparing(Score::getPosition))
        .limit(NUM_OF_INTEREST)
        .map(
            s -> {
              Player player = playerFetcher.fetchPlayer(s.getId());
              return String.format(
                  "Player %s starting in position %d with %d points",
                  player.getName(), s.getPosition(), s.getScore());
            })
        .collect(joining("\n"));
  }
}
