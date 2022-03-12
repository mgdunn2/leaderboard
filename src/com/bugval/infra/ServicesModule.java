package com.bugval.infra;

import com.bugval.services.ScoreFetcher;
import com.bugval.services.ScoreRepo;
import com.bugval.util.RequestScope;
import dagger.Module;
import dagger.Provides;

import java.net.http.HttpClient;

@Module
public class ServicesModule {
    @Provides @RequestScope
    public HttpClient provideHttpClient() {
        return HttpClient.newBuilder().build();
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
