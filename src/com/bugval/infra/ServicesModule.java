package com.bugval.infra;

import com.bugval.services.PlayerFetcher;
import com.bugval.services.ScoreFetcher;
import com.bugval.services.ScoreRepo;
import com.bugval.util.RequestScope;
import dagger.Module;
import dagger.Provides;

import java.net.http.HttpClient;

@Module
public class ServicesModule {
  /**
   * Provides an {@link HttpClient} that is reused throughout the duration of the request.
   */
  @Provides
  @RequestScope
  public HttpClient provideHttpClient() {
    return HttpClient.newHttpClient();
  }

  /**
   * Provide the Http impl of {@link ScoreFetcher}. Although the {@link HttpClient} dependency is request scope, the
   * class itself has no scope since it's internals are stateless.
   */
  @Provides
  public ScoreFetcher provideScoreFetcher(HttpScoreFetcher fetcher) {
    return fetcher;
  }

  /**
   * Provide the Http impl of {@link PlayerFetcher}. Although the {@link HttpClient} dependency is request scope, the
   * class itself has no scope since it's internals are stateless.
   */
  @Provides
  public PlayerFetcher providePlayerFetcher(HttpPlayerFetcher fetcher) {
    return fetcher;
  }

  /**
   * Provides the File System implementation of {@link ScoreRepo}. Since persistence is to the file system, the impl
   * itself can be provided with no scope.
   */
  @Provides
  public ScoreRepo provideScoreRepo(FsScoreRepo scoreRepo) {
    return scoreRepo;
  }

//  /**
//   * Provide the InMemoryScoreRepo as the impl for {@link ScoreRepo}. The implementation itself is Singleton scoped so
//   * that scores are saved between requests
//   */
//  @Provides
//  public ScoreRepo provideScoreRepo(InMemoryScoreRepo scoreRepo) {
//    return scoreRepo;
//  }
}
