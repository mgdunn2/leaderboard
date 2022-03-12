package com.bugval.infra;

import com.bugval.domain.Score;
import com.bugval.services.ScoreRepo;
import com.google.common.collect.ImmutableList;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class InMemoryScoreRepo implements ScoreRepo {
  private List<Score> scores;

  @Inject
  public InMemoryScoreRepo() {
    this.scores = ImmutableList.of();
  }

  @Override
  public List<Score> getScores() {
    return scores;
  }

  @Override
  public void saveScores(List<Score> scores) {
    this.scores = scores;
  }
}
