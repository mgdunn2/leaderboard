package com.bugval.infra;

import com.bugval.domain.Score;
import com.bugval.services.ScoreFetcher;
import com.google.gson.Gson;
import com.google.common.reflect.TypeToken;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.bugval.infra.ServicesModule.API_ADDRESS_PARAM;

public class HttpScoreFetcher implements ScoreFetcher {
  private final HttpClient client;
  private final Gson gson;
  private final String address;

  @Inject
  public HttpScoreFetcher(HttpClient client, Gson gson, @Named(API_ADDRESS_PARAM) String address) {
    this.client = client;
    this.gson = gson;
    this.address = address;
  }

  @Override
  public List<Score> fetchScores() {
    try {
      return gson.fromJson(
          client
              .send(
                  HttpRequest.newBuilder().GET().uri(URI.create(address)).build(),
                  HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8))
              .body(),
          new TypeToken<List<Score>>() {}.getType());
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
