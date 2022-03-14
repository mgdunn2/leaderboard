package com.bugval.services;

import com.bugval.domain.Score;

import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

/**
 * Repository for storing and loading the state of the last seen set of scores.
 */
public interface ScoreRepo {
  List<Score> getScores();

  void saveScores(List<Score> scores);

  default Map<String, Score> getScoresByPlayer() {
    return getScores().stream().collect(ImmutableMap.toImmutableMap(Score::getId, s -> s));
  }

  default Map<Integer, Score> getScoresByPosition() {
    return getScores().stream().collect(ImmutableMap.toImmutableMap(Score::getPosition, s -> s));
  }
}
