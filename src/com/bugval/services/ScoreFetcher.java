package com.bugval.services;

import com.bugval.domain.Score;

import java.util.List;

public interface ScoreFetcher {
  List<Score> fetchScores();
}
