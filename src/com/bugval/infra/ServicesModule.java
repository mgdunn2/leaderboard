package com.bugval.infra;

import com.bugval.services.ScoreFetcher;
import com.bugval.util.RequestScope;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;
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
}
