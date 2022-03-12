package com.bugval.infra;

import com.bugval.services.ScoreRepo;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class RepoModule {
    @Provides @Singleton
    public ScoreRepo provideScoreRepo(InMemoryScoreRepo scoreRepo) {
        return scoreRepo;
    }
}
