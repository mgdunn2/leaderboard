package com.bugval.infra;

import com.bugval.domain.Player;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import dagger.Module;
import dagger.Provides;

import javax.inject.Named;
import javax.inject.Singleton;

@Module
public class SingletonModule {
    static final String API_ADDRESS_PARAM = "ApiAddress";
    private static final String API_ADDRESS = "http://localhost:3000";

    @Provides
    @Named(API_ADDRESS_PARAM)
    public String provideApiAddress() {
        return API_ADDRESS;
    }

    /**
     * Provides an {@link Cache<String, Player>} that can be used to cache {@link Player}s by their ID. Use Cache rather
     * than LoadingCache since the CacheLoader cannot be provided at @Singleton scope
     */
    @Singleton
    @Provides
    public Cache<String, Player> providePlayerCache() {
        return Caffeine.newBuilder().build();
    }
}
