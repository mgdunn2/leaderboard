package com.bugval.infra;

import com.google.gson.Gson;
import dagger.Module;
import dagger.Provides;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;

import javax.inject.Singleton;
import java.util.ServiceLoader;

@Module
public class GsonModule {
  @Provides
  @Singleton
  public Gson commonBuilder() {
    GsonBuilder gsonBuilder = new GsonBuilder();
    for (TypeAdapterFactory factory : ServiceLoader.load(TypeAdapterFactory.class)) {
      gsonBuilder.registerTypeAdapterFactory(factory);
    }
    return gsonBuilder.create();
  }
}
