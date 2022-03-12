package com.bugval.services;

import com.bugval.domain.Score;

import javax.inject.Inject;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class IterationHandler {
  private static final int NUM_OF_INTEREST = 6;

  private final ScoreRepo scoreRepo;
  private final ScoreFetcher fetcher;

  @Inject
  public IterationHandler(ScoreRepo scoreRepo, ScoreFetcher fetcher) {
    this.scoreRepo = scoreRepo;
    this.fetcher = fetcher;
  }

  public void handleIteration() {
    List<Score> newScores = fetcher.fetchScores();
    if (scoreRepo.getScores().isEmpty()) {
      scoreRepo.saveScores(newScores);
      newScores.stream()
          .sorted(Comparator.comparing(Score::getPosition))
          .limit(NUM_OF_INTEREST)
          .forEach(
              s ->
                  System.out.printf(
                      "Player %s starting in position %d with %d points\n",
                      s.getId(), s.getPosition(), s.getScore()));
      System.out.println();
      return;
    }

    newScores.stream()
        .sorted(Comparator.comparing(Score::getPosition))
        .limit(NUM_OF_INTEREST)
        .flatMap(
            s -> {
              if (!scoreRepo.getScoresByPlayer().containsKey(s.getId())) {
                return Optional.of(
                    String.format(
                        "New Player %s moved into position %d with %d points",
                        s.getId(), s.getPosition(), s.getScore()))
                    .stream();
              } else if (scoreRepo.getScoresByPlayer().get(s.getId()).getPosition()
                  > s.getPosition()) {
                return Optional.of(
                    String.format(
                        "Player %s moved up to position %d with %d points",
                        s.getId(), s.getPosition(), s.getScore()))
                    .stream();
              } else if (scoreRepo.getScoresByPlayer().get(s.getId()).getPosition()
                  < s.getPosition()) {
                return Optional.of(
                    String.format(
                        "Player %s moved down to position %d with %d points",
                        s.getId(), s.getPosition(), s.getScore()))
                    .stream();
              }
              return Optional.empty().stream();
            })
        .forEach(System.out::println);
    System.out.println();
    scoreRepo.saveScores(newScores);
  }
}
