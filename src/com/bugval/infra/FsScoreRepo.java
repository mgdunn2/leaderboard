package com.bugval.infra;

import com.bugval.domain.Score;
import com.bugval.services.ScoreRepo;
import com.google.common.collect.ImmutableList;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import javax.inject.Inject;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Implementation of {@link ScoreRepo} that writes/reads state from the file system.
 */
public class FsScoreRepo implements ScoreRepo {
  private static final Path REPO_PATH = Paths.get("./scores.json");
  private final Gson gson;

  @Inject
  public FsScoreRepo(Gson gson) {
    this.gson = gson;
  }

  @Override
  public List<Score> getScores() {
    if (!REPO_PATH.toFile().exists()) {
      return ImmutableList.of();
    }
    try (InputStream inStream = new FileInputStream(REPO_PATH.toFile())) {
      return gson.fromJson(
          new String(inStream.readAllBytes(), StandardCharsets.UTF_8),
          new TypeToken<List<Score>>() {}.getType());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void saveScores(List<Score> scores) {
    try (OutputStream outStream = new FileOutputStream(REPO_PATH.toFile())) {
      outStream.write(gson.toJson(scores, new TypeToken<List<Score>>() {}.getType()).getBytes(StandardCharsets.UTF_8));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
