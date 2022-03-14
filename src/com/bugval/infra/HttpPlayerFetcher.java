package com.bugval.infra;

import com.bugval.domain.Player;
import com.bugval.services.PlayerFetcher;
import com.github.benmanes.caffeine.cache.Cache;
import com.google.gson.Gson;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import static com.bugval.infra.SingletonModule.API_ADDRESS_PARAM;

/**
 * Implementation of {@link PlayerFetcher} that uses {@link HttpClient} to fetch {@link Player}s and
 * caches them for future reads.
 */
public class HttpPlayerFetcher implements PlayerFetcher {
  private static final String PATH = "/api/players/";
  private final HttpClient client;
  private final Gson gson;
  private final String address;
  private final Cache<String, Player> playerCache;

  @Inject
  public HttpPlayerFetcher(
      HttpClient client,
      Gson gson,
      @Named(API_ADDRESS_PARAM) String address,
      Cache<String, Player> playerCache) {
    this.client = client;
    this.gson = gson;
    this.address = address;
    this.playerCache = playerCache;
  }

  @Override
  public Player fetchPlayer(String id) {
    return playerCache.get(id, this::fetchPlayerInternal);
  }

  private Player fetchPlayerInternal(String id) {
    try {
      return gson.fromJson(
          client
              .send(
                  HttpRequest.newBuilder().GET().uri(URI.create(uriString(id))).build(),
                  HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8))
              .body(),
          Player.class);
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  private String uriString(String id) {
    return address + PATH + id;
  }
}
