package com.bugval.services;

import com.bugval.domain.Score;

import java.util.List;

/**
 * Service responsible for fetching the most recent list of top 100 scores.
 */
public interface ScoreFetcher {
  List<Score> fetchScores();
}
