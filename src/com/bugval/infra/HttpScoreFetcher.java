package com.bugval.infra;

import com.bugval.domain.Score;
import com.bugval.services.ScoreFetcher;
import com.google.gson.Gson;
import com.google.common.reflect.TypeToken;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class HttpScoreFetcher implements ScoreFetcher {
  private final HttpClient client;
  private final Gson gson;

  @Inject
  public HttpScoreFetcher(HttpClient client, Gson gson) {
    this.client = client;
    this.gson = gson;
  }

  @Override
  public List<Score> fetchScores() {
    try {
      return gson.fromJson(
          client
              .send(
                  HttpRequest.newBuilder().GET().uri(new URI("http://localhost:3000")).build(),
                  HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8))
              .body(),
          new TypeToken<List<Score>>() {}.getType());
    } catch (IOException | InterruptedException | URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }
}
