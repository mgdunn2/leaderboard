package com.bugval.infra;

import com.bugval.services.ScoreFetcher;
import com.bugval.services.ScoreRepo;
import com.bugval.util.RequestScope;
import dagger.Module;
import dagger.Provides;

import javax.inject.Named;
import java.net.http.HttpClient;

@Module
public class ServicesModule {
  static final String API_ADDRESS_PARAM = "ApiAddress";
  private static final String API_ADDRESS = "http://localhost:3000";

  @Provides
  @Named(API_ADDRESS_PARAM)
  public String provideApiAddress() {
    return API_ADDRESS;
  }

  /**
   * Provides an {@link HttpClient} that is reused throughout the duration of the request.
   */
  @Provides
  @RequestScope
  public HttpClient provideHttpClient() {
    return HttpClient.newHttpClient();
  }

  @Provides
  public ScoreFetcher provideScoreFetcher(HttpScoreFetcher fetcher) {
    return fetcher;
  }

  @Provides
  public ScoreRepo provideScoreRepo(InMemoryScoreRepo scoreRepo) {
    return scoreRepo;
  }
}
